package br.unitins.topicos1.floricultura.model;

public enum TipoAdmin {

    OWNER(1, "Owner"),
    EMPLOYEE(2, "Employee");

    private final Integer id;
    private final String label;
    TipoAdmin(int id, String label) {
        this.id = id;
        this.label = label;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getLabel() {
        return label;
    }

    public static TipoAdmin valueOf(Integer id) {
        if (id == null)
          return null;
        for (TipoAdmin item : TipoAdmin.values()) {
          if (item.getId().equals(id))
            return item;
        }
        return null;
    }
}
