# Lielā Futbola Līga

[![Spring MVC](http://www.javapointer.com/wp-content/uploads/2016/05/spring.png?fit=225%2C150)](https://spring.io)

"Lielā Futbola Līga" ir Latvijas Universitātes Datorikas fakultātē uzdots 2. praktiskais darbs kursā "Modernās programmēšanas tehnoloģijas" (kursa pasniedzējs - Edgars Celms).

Praktiskā darba izveidei izvēlējos "Java Spring MVC" tehnoloģiju, jo nekad iepriekš nebiju darbojies ne ar Java, ne ar Spring MVC. Lai paplašinātu savas zināšanas, tika izvēlēta tieši šī tehnoloģija.

Galvenie darba uzdevumi:
  - Apstrādāt "Lielās Futbola Līgas" čempionāta divu spēļu raundu (viens raunds = 3 spēles) datus JSON vai XML formātos (dati tiek iedoti uzdevuma sākumā)
  - Izveidot lietotāja saskarni, kur var apskatīt informāciju par spēlēs gūtajiem vārtiem, komandām, spēlētājiem, tiesnešiem u.c.

Papildus tehnoloģijas, kuras izmantotas praktiskā darba izveidei:
  - Maven
  - Thymeleaf
  - Hibernate
  - Bootstrap HTML/CSS/JS framework v.3.3.7
  - PostgreSQL DB
 
### Projekta palaišana

Lai palaistu projektu, nepieciešams uzstādīt PostgreSQL

1. Noklonēt vai lejuplādēt repozitorija saturu
2. Uzstādīt PostgreSQL un pgAdmin
3. pgAdmin izpildīt skriptu `create_football_champ_db.sql`
4. Atvērt projektu Intellij IDEA (File->Open)
5. Failā `applicationContext.xml` nomainīt datubāzes paroli uz jūsu Postgres lietotāja paroli
6. Palaist projektu (`Run jetty:run`)
7. Pārlūkprogrammā atvērt [http://localhost:8080][localhost]

Kods un uzdevums ir [pieejams visiem][football] interesentiem.
Ja jums interesē izveidot savu uzdevuma risinājumu, pievienoju arī uzdevuma [nosacījumus][doc] un [čempionāta datus][data].

   [localhost]: <http://localhost:8080> 
   [football]: <https://github.com/AlekssGu/spring-mvc-football-championship>
   [doc]: <https://github.com/AlekssGu/spring-mvc-football-championship/blob/master/uzdevums.pdf>
   [data]: <https://github.com/AlekssGu/spring-mvc-football-championship/blob/master/dati.zip>