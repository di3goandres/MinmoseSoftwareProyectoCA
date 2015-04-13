Proyecto TSPi
Conceptos avanzados de Ingenieria de Software
Especializacion en construccion de Software
Universidad de los Andes

Contribuyentes:
Diego Montealegre: da.montealegre10@uniandes.edu.co
Alejandra Chica: am.chica10@uniandes.edu.co
Daniel Rentería: df.renteria10@uniandes.edu.co
Deivid Osorio: da.osorio12@uniandes.edu.co
Sebastian Cardona: s.cardona12@uniandes.edu.co


Instrucciones para obtener el repositorio desde github:
Hacer clone al proyecto con la siguiente URL: https://github.com/di3goandres/MinmoseSoftwareProyectoCA.git

Para ejecutar el programa localmente, ejecutar el siguiente comando:
cd MinmoseSoftwareProyectoCA
mvn package
java -cp target/MinmoseSoftwareProyectoCA-1.0-SNAPSHOT.jar edu.uniandes.ecos.tsp.vista.VistaTexto

Para ejecutar las pruebas automatizadas, ejecutar el siguiente comando:
cd MinmoseSoftwareProyectoCA
mvn clean install test -Dtest=AllTests -DfailIfNoTests=false

Para ejecutar el progama desde Heroku, ingresar a la siguiente URL: