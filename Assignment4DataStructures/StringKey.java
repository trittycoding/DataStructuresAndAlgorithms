public class StringKey implements Comparable<StringKey>{

    // Key used to create a hash value, used as Key K as entry
    String keyName;
    public String getKeyName() {return keyName;}
    public void setKeyName(String keyName) {this.keyName = keyName;}

    // Constructor
    public StringKey(String keyName){
        this.keyName = keyName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && this.getClass().equals(obj.getClass())){
            StringKey object = (StringKey) obj;
            if(this.getKeyName() == object.getKeyName()){
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(StringKey o) {
        return this.getKeyName().compareTo(o.getKeyName());
    }

    @Override
    public String toString() {
        return "KeyName: " + this.getKeyName() + " HashCode: " + this.hashCode();
    }

    @Override
    public int hashCode(){
        int hash = 0;
        for(int i = 0; i < getKeyName().length(); i++){
            hash += keyName.charAt(i)*power(31, i);
        }
        return Math.abs(hash);
    }

    public int power(int base, int p){
        int result = 1;
        for(int i = 0; i < p; i++){
            result *= base;
        }
        return result;
    }
}
