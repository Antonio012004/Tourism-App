# Proiect POO 2025

## Aplicație turistică

**Implementat de :** Sirbu-Cretu Antonio-Mihai

## Introducere

Acest proiect gestioneaza o simulare a unei baze de date in care se retin informatii despre diferite
muzee, dar si despre grupurile care le viziteaza. Scopul principal este organizarea
si gestionarea eficienta a acestor entitati, permitand interactiuni
structurate intre ele.

Aplicatia ofera functionalitati esentiale, cum ar fi:

- Adaugarea de muzee si gestionarea detaliilor acestora
- Crearea si administrarea grupurilor turistice
- Asocierea unui ghid fiecarui grup
- Gestionarea membrilor unui grup
- Simularea unui sistem de notificari pentru ghizii grupurilor

Un aspect important al aplicatiei este mecanismul de notificare: atunci cand un muzeu
organizeaza un eveniment special, fiecare ghid al unui grup turistic care
urmeaza sa viziteze acel muzeu primeste un mesaj automat cu detaliile
evenimentului. 

## Clase

### 1. **Database**
Clasa `Database` este utilizata pentru simularea interactiunii cu baza de date. Aceasta implementeaza design pattern-ul Singleton
pentru a preveni multipla instantiere a acesteia.


### 2. **Museum**
Clasa `Museum` se ocupa ce gestionarea datelor cunoscute pentru fiecare muzeu
. Este implementata cu design pattern-ul builder care creeaza posibilitatea de a
acorda fiecarei instante de muzeu, anumite atribute optionale. De asemenea un muzeu
gestioneaza si o lista de ghizi, pentru a putea implementa sistemul de notificari.

### 3. **Location**
Clasa `Location` gestioneaza specifice muzeelor, si aceasra este implementata cu design pattern-ul 
Builder pentru a permite fiecarui obiect de tip locatie sa aiba atat atribute obligatorii, cat si
optionale.

### 4. **Person**
Clasa `Person` este clasa de baza gestionand fiecare tip de persoana din aceasta baza de date, aceasta
retine informatii esentiale precum numele, prenumele, rolul, email-ul si varsta.

### 5. **Student**
Clasa `Student` extinde `Person` si adauga informatii specifice studentilor precum anul de studii si scoala.

### 6. **Professor**
Clasa `Professor` extinde `Person` si adauga informatii despre anii de experiente si scoala, aceasta
implementeaza si interfata Observer pentru a scrie in fisier atunci cand este notificat profesorul ghid
al unui grup


### 7. **Group**
Clasa `Group` reprezinta clasa aferenta grupurilor, retine date precum codul muzeului, 
lista de membrii, intervalul de vizitare si ghidul grupului

### 8. **PersonFactory**

Clasa `PersonFactory` implementeaza design pattern-ul Factory Method, pentru a gestiona ce tip
de persoana sa se instantieze.

### 9. **FunctionManager**

Clasa  `FunctionManager` ajuta la modularizarea codului si implementeaza functiile specifice fiecarei comenzi,
precum procesarea unui muzeu, a unui grup, transmiterrea evenimentelor care apar.

## Gestionarea Excepțiilor

### Excepții Implementate
| Excepție | Mesaj |
|----------|------|
| `GroupNotExistsException` | "Group does not exist." |
| `GroupThresholdException` | "Group cannot have more than 10 members." |
| `GuideExistsException` | "Guide already exists." |
| `GuideTypeException` | "Guide must be a professor." |
| `PersonNotExistsException` | "Person was not found in the group." |

Pe langa mesajul fiecarei exceptii se trimite ca parametru si un mesaj in functie de comanda data
pentru a afisa o exceptie customizata pentru fiecare comanda care se incearca. De exemplu daca comanda care esueaza e
ADD MEMBER si e exceptia `GroupThresholdException` pe langa mesajul aferent se afiseaza si `new member: ` pentru a stii
ce comanda s-a incercat.

## Interfete

### 1. **Subject**
Interfata `Subject` este utilizata pentru a defini metodele specifice subiectilor
care vor fi observati, adica muzeele, aceasta are cele 3 metode de baza ale interfetei pentru
implementarea design pattern-ului Observer, adica attachObserver, detachObserver si
notifyObservers.

## Design Patterns Utilizate

| Design Pattern | Clasa unde a fost folosit | Justificare                                                                                            |
|----------------|---------------------------|--------------------------------------------------------------------------------------------------------|
| **Singleton**  | `Database`                | Previne instantierea multipla a bazei de date                                                          |
| **Builder**    | `Museum` si `Location`    | Se ofera posibilitatea crearii instantelor obiectelor de tip muzeu si locatie, intr-un mod mai complex |
| **Observer**   | `Museum` si `Professor`   | Notifica ghizii despre aparitia evenimentelor                                                          |
| **Factory**    | `PersonFactory`           | L-am folosit pentru instantierea controlata a obiectelor de tip Student/Professor                      |

### Explicatie amanuntita

- Am utilizat `Singleton` deorece o baza de date a unei aplicatii nu ar trebui sa aiba mai multe instante, astfel
prin acest design pattern se previne multipla instantiere a obiectelor de acest tip.
- Am utilizat design pattern-ul `Builder` pentru creerea obiectelor de tip **Museum** si **Location** pentru a da un nivel
de complexitate acestor clase, oferind posibilitatea de a avea si campuri care nu trebuie sa existe obligatoriu, cum se practica de asemenea la o aplicatie similara.
- Am oferit interfetele `Observer ` si `Subject` pentru a facilita implementarea design pattern-ului **Observer**. Fiecare obiect
de tip *Museum* are o lista de ghizi pentru a stii cui sa transmita mesajele atunci cand apare un eveniment. In clasa *Museum* se implementeaza metodele specifice interfetei `Subject`, 
metode de atasarea, detasarea si notificarea observatorilor. In clasa *Professor* se implementeaza interfata `Obsever` astfel incat atunci cand profesorii ghizi primesc mesajul, acestia scriu in fiser informatiile acestuia.
- Design pattern-ul `Factory` l-am utiliza pentru a gestiona mai usor clasele ce mostenesc Person, adica *Student* si *Professor*. Obiectele sunt instantiate in functie de rolul pe care persoanele il au in grupul respectiv, 
de asemenea daca persoana este manager-ul muzeului se va instantia un obiect de tip Person. Astfel am reusit sa realizez creerea de instante mult mai generica si mai usor de realizat.

## Concluzie

Proiectul dezvoltat reprezinta o aplicatie turistica complexa, care simuleaza gestionarea unui sistem de muzee si grupuri turistice, si ofera posibilitatea de notificarea la aparitia unui eveniment. Se utilizeaza conceptele de baza ale POO-ului, dar si concepte mai
complexe precum design pattern-uri. Aplicatia se ocupa si de gestionarea unor exceptii posibile in incercarea de manipularea a bazei de date. 


