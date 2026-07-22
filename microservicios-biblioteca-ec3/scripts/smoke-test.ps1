$ErrorActionPreference = "Stop"
$tokenResponse = Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8090/realms/biblioteca/protocol/openid-connect/token" `
  -ContentType "application/x-www-form-urlencoded" `
  -Body @{ client_id="biblioteca-postman"; grant_type="password"; username="admin"; password="admin123" }
$headers = @{ Authorization = "Bearer $($tokenResponse.access_token)" }

$isbn = "978-612-" + (Get-Random -Minimum 100000 -Maximum 999999)
$libroBody = @{ isbn=$isbn; titulo="Libro de prueba EC3"; autor="Autor Demo"; categoria="Tecnología"; anioPublicacion=2026 } | ConvertTo-Json
$libro = Invoke-RestMethod -Method Post -Uri "http://localhost:8081/api/libros" -Headers $headers -ContentType "application/json" -Body $libroBody
Write-Host "Libro creado: $($libro.id)"

$fechaLimite = (Get-Date).AddDays(7).ToString("yyyy-MM-dd")
$prestamoBody = @{ libroId=$libro.id; lectorNombre="Carlos Perez"; lectorDocumento="76543210"; fechaLimite=$fechaLimite } | ConvertTo-Json
$prestamo = Invoke-RestMethod -Method Post -Uri "http://localhost:8082/api/prestamos" -Headers $headers -ContentType "application/json" -Body $prestamoBody
Write-Host "Prestamo creado: $($prestamo.id), disponible=$($prestamo.libro.disponible)"

$devolucion = Invoke-RestMethod -Method Post -Uri "http://localhost:8082/api/prestamos/$($prestamo.id)/devolucion" -Headers $headers
Write-Host "Devolucion: estado=$($devolucion.estado), multa=$($devolucion.multa), disponible=$($devolucion.libro.disponible)"
Write-Host "PRUEBA COMPLETA EXITOSA"
