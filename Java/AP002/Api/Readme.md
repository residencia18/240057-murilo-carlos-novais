API de Gerenciamento de Usuários
Esta API oferece endpoints simples e seguros para operações de autenticação e gerenciamento de contas de usuário.

Endpoints Disponíveis
Registro de Usuário
Endpoint:
POST /api/auth/register

Payload de Requisição (JSON):
{"username": "example", "email": "user@example.com", "password": "password"}

Resposta de Sucesso (200 OK):
{"message": "User registered successfully!"}

Login de Usuário
Endpoint:
POST /api/auth/login

Payload de Requisição (JSON):
{"username": "example", "password": "password"}

Resposta de Sucesso (200 OK):
{"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"}

Solicitação de Recuperação de Senha
Endpoint:
POST /api/password-recovery

Payload de Requisição (JSON):
{"email": "user@example.com"}

Resposta de Sucesso (200 OK):
{"message": "Password reset completed!"}

Redefinição de Senha
Endpoint:
POST /api/password-reset

Payload de Requisição (JSON):
{"token": "validToken", "newPassword": "newPassword"}

Resposta de Sucesso (200 OK):
"Password reset successfully!"

Documentação da API
Para mais detalhes sobre os modelos de dados e os endpoints disponíveis, consulte a documentação da API em http://localhost:8080/swagger-ui/.