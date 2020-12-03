import java.text.DecimalFormat;

public class Item implements Comparable<Item>{
    // Variables
    String name;
    int goldPieces;
    double weight;

    // Constructor for an item
    public Item(String name, int goldPieces, double weight){
        this.name = name;
        this.goldPieces = goldPieces;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int getGoldPieces() {
        return goldPieces;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
//        if(obj == null){
//            throw new NullPointerException("Object is null");
//        }

        if(obj != null && this.getClass().equals(obj.getClass())){
            Item object = (Item) obj;

            boolean nameComparison = this.name == object.name;
            boolean goldComparison = this.goldPieces == object.goldPieces;
            boolean weightComparison = this.weight == object.weight;

            // If all three comparisons pass, they're equal
            if(nameComparison && goldComparison && weightComparison){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name + " is worth " + this.goldPieces + "gp and weighs " + Math.round(this.getWeight()) + "kg";
    }

    @Override
    public int compareTo(Item other) {
        return this.name.compareTo(other.name);
    }
}
