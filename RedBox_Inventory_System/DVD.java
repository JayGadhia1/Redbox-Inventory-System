public class DVD implements Comparable<DVD> {
    private String title;
    private int available;
    private int rented;

    public DVD(String title, int available, int rented) {
        this.title = title;
        this.available = available;
        this.rented = rented;
    }

    // Accessors
    public String getTitle() {
        return title;
    }

    public int getAvailable() {
        return available;
    }

    public int getRented() {
        return rented;
    }

    // Mutators
    public void setAvailable(int available) {
        this.available = available;
    }

    public void setRented(int rented) {
        this.rented = rented;
    }

    public int compareTo(DVD other) {
        return this.title.compareTo(other.title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DVD dvd = (DVD) obj;
        return title.equals(dvd.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return title + " " + available + " " + rented;
    }
}
