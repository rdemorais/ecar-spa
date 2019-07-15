settings.xml

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

Levantar servidor:

mvn tomcat6:run -Pdev -DskipTests=true

Levantar app:

gulp server