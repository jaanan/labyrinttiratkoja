package labyrintti.algoritmit.generointi;
import java.util.*;
import labyrintti.malli.Pala;
import static labyrintti.malli.Pala.Tyyppi.KULKU;
import static labyrintti.malli.Pala.Tyyppi.MUURI;

// TÄTÄ EI OLE VIELÄ YHDISTETTY LABYRINTTIIN

public class PriminAlgoritmi {
    private int korkeus;
    private int leveys;
    private char [][] maz;
    private StringBuilder tulosta;
    private Pala alku;
    private Pala loppu;

    public PriminAlgoritmi(int korkeus, int leveys) {
        // gemeroitavan sokkelon mitat
        this.korkeus = korkeus;
        this.leveys = leveys;
        this.maz = new char[korkeus][leveys];
    }    
    public Pala[] generoi() {
    // rakennetaan sokkelo ja alustetaan se seinillä
    StringBuilder sb = new StringBuilder(leveys);
    for (int x = 0; x < leveys; x++)
    sb.append('*');
    for (int x = 0; x < korkeus; x++) maz[x] = sb.toString().toCharArray();
    prim();
    var i = 0;
    Pala[] primmaze = new Pala[korkeus*leveys];
        for(int x = 0; x < korkeus; x++) {
            for(int y = 0; y < leveys; y++){
                if (maz[x][y] == '*'){
                    primmaze[i] = new Pala(x, y, MUURI);
                    i++;
                } else if (maz[x][y] == 'S') {
                    alku = new Pala(x, y, KULKU);
                    primmaze[i] = alku;
                    i++;
                } else if (maz[x][y] == 'E') {
                    loppu = new Pala(x, y, KULKU);
                    primmaze[i] = loppu;
                    i++;
                } else {
                    primmaze[i] = new Pala(x, y, KULKU);
                    i++;
                }
            }
        }
    return primmaze;
    }    

    public void prim() {
        // valitaan alkupiste
        int alku = 0;
        int piste = 1;
        Point st = new Point(alku, piste, null);
        maz[st.r][st.c] = 'S';

        // iteroidaan solmun suorat naapurit läpi
        ArrayList <Point> frontier = new ArrayList <Point> ();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                    try {
                        if (maz[st.r + x][st.c + y] == '.') continue;
                    } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                        continue;
                    }
        // lisätään kelvolliset solmut rajalle
        frontier.add(new Point(st.r + x, st.c + y, st));
        }

        while (!frontier.isEmpty()) {

        // valitaan nykyinen solmu sattumanvaraisesti
        Point cu = frontier.remove((int)(Math.random() * frontier.size()));
        Point op = cu.opposite();
            try {
                // jos sekä solmu että sen vastakkainen solmu ovat muuria 
                if (maz[cu.r][cu.c] == '*') { // mites näihin if lauseisiin sai sen tai merkin? | ehkä
                    if (maz[op.r][op.c] == '*') {

                        // avataan solmujen välille polku
                        maz[cu.r][cu.c] = '.';
                        maz[op.r][op.c] = '.';

                        // iteroidaan solmun suorat naapurit, sama kuin aiemmin
                        for (int z = -1; z <= 1; z++) {
                            for (int q = -1; q <= 1; q++) {
                                if (z == 0 && q == 0 || z != 0 && q != 0) {
                                    continue;
                                } try {
                                    if (maz[op.r + z][op.c + q] == '.') continue;
                                } catch (Exception e) {
                                        continue;
                                }    
                                frontier.add(new Point(op.r + z, op.c + q, op));
                            }
                        }    
                    }
                } 
            } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
            } if (frontier.isEmpty()) {
                maz[op.r][op.c] = 'E';
            }
        } 
    }
    for (int i = 0; i < korkeus; i++) {
        for (int j = 0; j < leveys; j++)
            System.out.print(maz[i][j]);
            System.out.println();
    }

}

    // tulostetaan lopullinen maze
    public String toString(){
        StringBuilder ylin = new StringBuilder(leveys);
        StringBuilder alin = new StringBuilder(korkeus*leveys);
        this.tulosta = new StringBuilder(korkeus*leveys);
        for (int i = 0; i < korkeus; i++) {
            tulosta.append("*");
            for (int j = 0; j < leveys; j++) {
                tulosta.append(maz[i][j]);                    
            }
            tulosta.append("*\n");
        }
        ylin.append("*");
        for (int i = 0; i < leveys; i++) {
            if (i == 1) {
                ylin.append("S");
            } else {
                ylin.append("*");
            }
        }
        ylin.append("*\n");
        alin.append("*");
        for (int i = 0; i < leveys; i++) {
            if (i == leveys-2) {
                alin.append("E");
            } else {
                alin.append("*");
            }
        }
        alin.append("*\n");    
        return ylin+tulosta.toString()+alin;
    }      

    static class Point {
        Integer r;
        Integer c;
        Point parent;
        public Point(int x, int y, Point p) {
            r = x;
            c = y;
            parent = p;
            }
    // lasketaan, onko annettu vastakkainen solmu eri suuntaan parent-solmusta katsottuna.
        public Point opposite() {
            if (this.r.compareTo(parent.r) != 0)
                return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
            if (this.c.compareTo(parent.c) != 0)
                return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
            return null;
        }
    }   
}
