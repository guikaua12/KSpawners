# spawners
Plugin de spawner relativamente simples porém funcional e com customização de inventário e mensagens.

### Versões compatíveis
|1.8|1.9|1.11|1.15|1.16|1.17|1.18|
|---|---|----|----|----|----|----|

### Comandos
|Comando|Permissão|Descrição|
|-------|---------|---------|
|/spawneradmin|spawners.admin|Mostra os comandos disponíveis para admins|
|/spawneradmin givespawner|spawners.admin|Dá spawners para um jogador específico|

### Dependências usadas
- [NBTInjector](https://github.com/tr7zw/Item-NBT-API) - Usado pra injetar NBT customizada em entidades nas versões abaixo da 1.14
- [NBT-API](https://github.com/tr7zw/Item-NBT-API) - Usado para facilitar o uso do NBT em itens/entidades
- [Configuration Injection](https://github.com/HenryFabio/configuration-injector) - Injeta valores da config na memória
- [Command Framework](https://github.com/saiintbrisson/command-framework) - API pra criação de comando sem boilerplate
- [Inventory Framework](https://github.com/DevNatan/inventory-framework) - API pra criação de inventário sem boilerplate
- [SQL Provider](https://github.com/Jaoow/sql-provider) - API pra conexão com SQL
- [Lombok](https://github.com/projectlombok/lombok) - Gera getter/setter e construtores através de anotações
