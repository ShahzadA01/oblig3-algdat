# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Ali Shahzad, S351935, s351935@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så gikk jeg frem ved å bruke programkoden fra 5.2.3 a). Forskjellen her er at når
vi oppretter en ny node, legger vi til q som forelder noden slik at forelder pekeren blir riktig.

I oppgave 2 så brukte jeg koden fra metoden inneholder(). Jeg initaliserte en teller-variabel som holder
følge på hvor mange forekomster verdien har i treet. Vi legger 1 til telleren når
cmp == 0 siden da er søkeverdien og verdien i noden p lik. Vi går videre til høyre
for å fortsette gjennom treet. Vi fortsetter til høyre så lenge søkeverdien er større
enn eller lik verdien i noden vi er i. Går til venstre hvis den er mindre frem til vi er 
ute av treet. Når while-løkken stopper, returnerer vi teller variabelen som forteller
oss om antall forekomster av søkeverdien.

I oppgave 3 så bruker jeg programkode 5.1.7 h) fra kompendiet. Vi vet at den første i postorden
er den noden som ligger lengst til venstre, så vi går så langt til venstre i treet som vi 
kan og returnerer den noden, p, som ligger lengst til venstre.

Når vi skal finne neste i postorden bruker jeg en sjekkliste for å finne neste i postorden (5.1.7 i kompendiet).
Jeg initaliserer en node jeg kaller for neste som holder verdien til den neste i postorden.
Hvis p ikke har en forelder node (p.forelder == null) har vi kommet til rotnoden, altså
den siste noden i postorden så vi returnerer null.

Sjekker deretter om p er høyre barn, hvis det stemmer er neste p.forelder. Hvis
p er et venstre barn må vi sjekke om det er et alenebarn eller om et høyre barn eksisterer
for p.forelder. Hvis p.forelder.høyre != null er neste i postorden den noden som kommer
første i postorden i subtreet til høyre barnet.

Når vi har gått gjennom sjekklisten, returnerer vi neste-noden.

I oppgave 4 har jeg bestemt at oppgaven er å skrive ut treet i post orden. 
I den iterative metoden finner jeg første node i post orden fra roten, og skriver
den verdien ut. Bruker en while-løkke som går til neste node i post orden og skriver ut.

I den rekursive metoden starter med å utføre oppgaven i rotnoden. Jeg kaller deretter på barne nodene til
p og fortsetter slik inntil p == null.

I oppgave 5 starter jeg av ved å initalisere en ArrayList som skal holde alle verdiene til
det binære treet. Deretter initaliserer jeg en kø hvor jeg legger inn nodene. Jeg legger til
rotnoden først. I en while-løkke fjerner vi verdier fra køen og legger dem inn i arrayet
og legger til venstre og høyre barn til køen dersom de eksisterer. I while-løkken vil de
bli tatt ut av køen og lagt inn i arrayet. Returnerer arrayet når vi er ferdige.

Når vi skal gjøre det om til et tre igjen, initaliserer jeg først et nytt binært tre som jeg
kaller for tre (veldig originalt) med comparator. Bruker en foreach-løkke som legger
verdiene fra arrayet inn i treet. Når vi er ferdige returnerer vi treet.

I oppgave 6 bruker jeg programkode 5.2.8 d) fra kompendiet. Eneste endringen jeg gjør er å legge
til if-setninger som setter forelder-peker for nodene når vi fjerner dem. I tilfelle 1 og 2
setter jeg b sin forelder-peker lik q siden jeg blir kvitt p, dersom b ikke er null. jeg gjør
det samme for tilfelle 3, men jeg setter i stedet r.høyre.forelder sin peker til s.

For fjernalle() sjekker jeg først om treet er tomt. Deretter initialiserer vi en teller-variabel
som teller antall noder jeg fjernet som hadde verdien vi skulle fjerne. Bruker en while-løkke
som fjerner verdien så lenge fjern-metoden klarer å finne verdien. Legger 1 til telleren.
Når while-løkken er ferdig, returnerer jeg antall verdier vi slettet.

I nullstill() sjekker jeg igjen om treet er tomt, siden vi ikke trenger å nullstille et tomt
tre. Deretter initaliserer jeg en node, p, som starter ved rotnoden. Initialiserer
også en stopper-variabel som jeg setter lik antall (samme som lengden av et array).
Stopper-variabelen bruker jeg for while-løkken slik at jeg kan fjerne alle verdiene i treet.
Jeg finner første node i post orden, og fjerner den i løkken, finner neste i post orden 
og trekker 1 fra stopper variabelen frem til den er lik 0. Da har vi nullstilt hele treet
og det er ingen noder igjen.

