package org.misha;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String profession;

    public User() {
    }

    public User(int id, String name, String profession) {
        this.id = id;
        this.name = name;
        this.profession = profession;
    }

    int getId() {
        return id;
    }

    @XmlElement
    void setId(int id) {
        this.id = id;
    }

    private String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    private String getProfession() {
        return profession;
    }

    @SuppressWarnings("unused")
    @XmlElement
    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (!(object instanceof User)) {
            return false;
        } else {
            User user = (User) object;
            if (id == user.getId() && name.equals(user.getName()) && profession.equals(user.getProfession())) {
                return true;
            }
        }
        return false;
    }
}