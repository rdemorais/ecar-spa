Java 8

Postgres

Maven

.m2/settings.xml

<profiles>
  <profile>
    <id>dev</id>
    <properties>
      <jdbc-url>jdbc:postgresql://localhost:5432/ecar2017</jdbc-url>
      <jdbc-username>sa_ecar</jdbc-username>
      <jdbc-passwd>PASS</jdbc-passwd>
      <pems-url>http://localhost:8080/pems</pems-url>
    </properties>
  </profile>
</profiles>

user: monica.silva
pass: serenaya

Levantar servidor:

pe-server/

mvn tomcat6:run -Pdev -DskipTests=true

Levantar app:

pe-spa/

gulp webserver

GULP:

[14:23:03] CLI version 3.9.1
[14:23:03] Local version 3.9.1