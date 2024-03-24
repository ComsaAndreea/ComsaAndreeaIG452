# Hotel
Reservierungsverwaltung

Ein Hotel braucht eine Anwendung, mit Hilfe welcher sich Kunden Reservierungen machen können.
Dazu kann sich ein Hotel die Reservierungen verwalten und in Ordnung halten.
Jedes Hotel hat einen Id , Name, 4 Standardpreise für jeden Zimmetyp (Single-Room, Double-Room,
Triple-Room, Apartament) und man gibt die Möglichkeit Zimmer einzufügen und löschen. 
Jedes Hotel hat eine Liste von Zimmern. Ein Zimmer hat einen Id, einen Typ, einen Preis, eine Anzahl von
Personen, die in einem Zimmer passen, und eine Liste von Zeitspannen in denen das Zimmer besetzt
ist. 
Jeder Kunde ist eine Person, die auch eine Liste von Rabatt Coupons hat. Ein solcher Coupon
identifiziert sich durch eine Code und einen Prozentansatz.

1. Die Anwendung ermöglicht es den Kunden sich eine Reservierung für eine bestimmte
Zeitspanne zu machen. Die Kunden können aus mehreren Möglichkeiten, die Reservierung
zu veranstalten, wählen, diese werden Optionen genannt. Eine Option besteht aus eine Liste
von Zimmern welche entsprechend einer gewählten Zeitspanne und Personenanzahl
selektiert sind. Eine Option kann folgendermaßen aussehen: Option1 (id:1 [Apartament],
Total Price: 300), Option2 (id: 2, [Triple, Single], Total Price: 450) … Optionn (ID: n, [Single,
Single, Single, Single], Total Price: 700). Ein Kunde kann eine Reservierung machen indem er
eine Option wählt.

2. Falls ein Zimmer reserviert wird dann wird ihre disponibilität verändert.

3. Mit der Anwendung kann ein Kunde alle seine Reservierungen sehen.

4. Mit der Anwendung kann man alle Zimmern aus einen Hotel besichtigen.

5. Bem Reservieren muss man die check-in und check-out Daten, und die Anzahl der Personen,
die zur Reservierung gehören, angeben. Das Programm informiert die Kunden über
Folgendes:
       a. Man habe nicht genug verfügbare Zimmer für alle Personen
      
       b. Falls genung Zimmern sind, bekommt man eine Liste von Optionen für
          Reservierungen
        
       c. Man könne am Ende auch Rabatt Coupons einfügen
       
       d. Bei jeden 3 gemachten Reservierungen bekommt ein Kunde ein Rabatt Coupon


Klassendiagramm
-----------------------------------------------------------------------------------------------------------------------------------------
<img width="922" alt="image" src="https://user-images.githubusercontent.com/52560295/211395172-9b76f6a7-ba6f-4f35-9af2-375da4366197.png">



