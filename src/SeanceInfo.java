public class SeanceInfo {
    private int id;
    private String nomCours;
    private String nomClasse;
    private String dateSeance;
    private String contenu;
    private String statutValidation;

    public SeanceInfo(int id, String nomCours, String nomClasse, String dateSeance, String contenu, String statutValidation) {
        this.id = id;
        this.nomCours = nomCours;
        this.nomClasse = nomClasse;
        this.dateSeance = dateSeance;
        this.contenu = contenu;
        this.statutValidation = statutValidation;
    }

    public int getId() { return id; }
    public String getNomCours() { return nomCours; }
    public String getNomClasse() { return nomClasse; }
    public String getDateSeance() { return dateSeance; }
    public String getContenu() { return contenu; }
    public String getStatutValidation() { return statutValidation; }
}
