package JsonForModules;

public class UpdateStore {
    public String name;
    boolean can_local_pickup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCan_local_pickup() {
        return can_local_pickup;
    }

    public void setCan_local_pickup(boolean can_local_pickup) {
        this.can_local_pickup = can_local_pickup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String address;
    public String phone;
}
