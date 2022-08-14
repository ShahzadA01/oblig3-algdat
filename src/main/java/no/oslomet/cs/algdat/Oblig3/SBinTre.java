package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    public static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        //Programkode 5.2.3a fra kompendiet.
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!"); // sørger for at vi ikke får null-verdier

        Node<T> p = rot, q = null;
        int cmp = 0;

        while (p != null){
            q = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre: p.høyre;
        }

        p = new Node<>(verdi, q); //q blir forelder til p

        if (q == null) rot = p; // hvis q (p.forelder) er null, er p rotnoden
        else if (cmp < 0) q.venstre = p; // p er venstre barnet til q (p < q)
        else q.høyre = p; // p er høyre barnet til q (p=>q)

        antall++;
        return true;
    }

    public boolean fjern(T verdi) {
        //Programkode 5.2.8 d) fra kompendiet.

       if (verdi == null) return false; //treet har ingen nullverdier

       Node<T>  p = rot, q = null; //q skal være forelder til p

       while (p != null){ // leter etter verdi

           int cmp = comp.compare(verdi, p.verdi);  //sammenligner
           if (cmp < 0) { q = p; p = p.venstre;} //går til venstre

           else if (cmp > 0) {  // går til høyre
                q = p; p = p.høyre;}

           else break; // den søkte verdien ligger i p
           }
       if (p == null) return false; //finner ikke verdi

       if (p.venstre == null || p.høyre == null){ // tilfelle 1) og 2)

           Node<T> b = p.venstre != null ? p.venstre : p.høyre; // b for barn

           if (p == rot) rot = b;

           else if (p == q.venstre) q.venstre = b;

           else q.høyre = b;

           if (b != null){ // gir barn-noden forelder peker til q.
               b.forelder = q;
           }

        }
       else{ //tilfelle 3)
           Node<T> s = p, r = p.høyre; // finner neste i inorden
           while (r.venstre != null){
               s = r; // s er forelder til r
               r = r.venstre;
           }

           p.verdi = r.verdi; // kopierer verdien i r til p

           if (s != p){
               s.venstre = r.høyre;
               if(r.høyre != null){
                   r.høyre.forelder = s;
               }
           }
           else{
               s.høyre = r.høyre;
               if(r.høyre != null){
                   r.høyre.forelder = s;
               }
           }
       }

       antall--; // det er nå én node mindre i treet
       return true;
    }

    public int fjernAlle(T verdi) {
        if (tom()){ //Sjekker om treet er tomt, fjerner ingenting hvis det er tomt.
            return 0;
        }

        int teller = 0; //Initaliserer teller variabel for antall verdier vi fjerner.

        while (fjern(verdi)){ //Kjører så lenge verdien ligger i treet, og fjerner den
            teller++; //Legger til 1 for hver gang vi fjerner verdien
        }
        return teller; //returnerer antall verdier vi har fjernet
    }

    public int antall(T verdi) {
        //Bruker samme kode som metoden inneholder().
        //Initaliserer en teller variabel som returnerer antall forekomster av verdien.
        int teller = 0;
        //Null er ikke i treet. Returnerer 0.
        if (verdi == null) return 0;

        Node<T> p = rot;

        //Sammenligner verdi med verdien i noden p. Hvis cmp == 0 er verdiene like og vi legger 1 til teller
        //Siden verdi og p.verdi er samme verdi går vi til høyre i treet.
        while (p != null){
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp == 0){
                teller++;
                p = p.høyre;
                //Hvis verdien er større enn p.verdi fortsetter vi videre i treet (cmp > 0)
            } else if (cmp > 0) {
                p = p.høyre;
                //Fortsetter til venstre hvis cmp < 0.
            } else {
                p = p.venstre;
            }
        }
        //Returnerer antall forekomster.
     return teller;
    }

    public void nullstill() {

        if (tom()){ //Sjekker om treet er tomt, ingen vits å gjøre noe hvis det er tomt
            return;
        }

        Node<T> p = rot; //Starter ved rotnoden

        int stopper = antall; //Initaliserer en stopper variabel som jeg setter lik antall

        p = førstePostorden(p); //Finner første node i postorden

        while (stopper != 0){ // Bruker stopper-variabelen for while-løkken, mens stopper > 0 vil vi fjerne alle noder
            fjern(p.verdi); //Fjerner noden og setter verdien lik null.
            p.verdi = null;

            p = nestePostorden(p); //Finner neste node i postorden og repeterer
            stopper--; //Nå mindre antall noder i treet.
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p, "P kan ikke være null!");
        //Bruker programkode 5.1.7h fra kompendiet.
        //Initaliserer ikke p = rot som i kompendiet siden vi får inn en node.
        //Vet at den første i postorden er den noden som ligger lengst til venstre i treet
        //Går så langt til venstre som vi kan, og
        while (true){
            if (p.venstre != null) p = p.venstre;
            else if(p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Objects.requireNonNull(p, "P kan ikke være null!");

        Node<T> neste = p; //initaliserer en node, neste, som holder verdien for neste i postorden

        if (p.forelder == null) return null; // neste i postorden er rotnoden

        if (p == p.forelder.høyre) neste = p.forelder; // p er høyre barn, p.forelder er neste

        if (p == p.forelder.venstre){ //må sjekke om p er alenebarn eller om det er et høyre barn

            if (p.forelder.høyre == null) neste = p.forelder; //p er alenebarn, p.forelder er neste

            else if (p.forelder.høyre != null){

                neste = førstePostorden(p.forelder.høyre); //neste er første i postorden i subtreet til høyre barn
            }
        }
        return neste; //returnerer den neste i postorden.
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = førstePostorden(rot); //Finner første node i postorden
        Objects.requireNonNull(p, "Treet er tomt!");

        oppgave.utførOppgave(p.verdi); // Skriver ut første node i postorden
        while(p.forelder != null){ // Når p.forelder == null har vi kommet til rot-noden, altså siste node i postorden
            p = nestePostorden(p); // finner neste node i postorden
            oppgave.utførOppgave(p.verdi); //skriver ut verdien
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null){
            return;
        }
        //kaller rekursivt på barn nodene til p
        postordenRecursive(p.venstre, oppgave);

        postordenRecursive(p.høyre, oppgave);
        //utfører oppgaven
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() { //bruker programkode 5.1.6a) som utgangspunkt

        ArrayList<T> array = new ArrayList<>(); //initaliserer arrayet hvor verdiene legges i

        Queue<Node<T>> queue = new LinkedList<>(); //initialiserer en kø
        queue.add(rot); //legger til rot,

        while (!queue.isEmpty()){ //kjører så lenge køen ikke er tom

            Node<T> p = queue.remove(); // fjerner fra køen
            array.add(p.verdi); //legger til i arrayet

            if (p.venstre != null) queue.add(p.venstre); //venstre barn eksisterer, legger til i kø
            if (p.høyre != null) queue.add(p.høyre); //høyre barn eksisterer, legger til i kø
        }
        return array; // returnerer arrayet
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tre = new SBinTre<>(c); //lager et nytt binært tre

        for (K verdi : data){ // for-each løkke som legger verdier inn i treet
            tre.leggInn(verdi);
        }
        return tre;
    }



} // ObligSBinTre
