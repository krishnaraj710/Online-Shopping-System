import java.util.*;
import java.io.*;
import mypackage.*;

class Main 
{
    private static void saveObjectsToFile1(ArrayList<Accounts> object, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
            System.out.println("Objects saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveObjectsToFile2(ArrayList<Product> object, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
            System.out.println("Objects saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object loadObjectsFromFile1(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return(ArrayList<Accounts>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static Object loadObjectsFromFile2(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return(ArrayList<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void main(String args[])
    {
        ArrayList<Accounts> a = new ArrayList<>();
        ArrayList<Product> p = new ArrayList<>();
        a.add(new Accounts("Walter White", "9865877516", "ww", 1));
        a.add(new Accounts("Jesse Pinkman", "9865877510", "jp", 0));
        a.add(new Accounts("Saul Goodman", "9865877511", "sg", 1));
        a.add(new Accounts("Tony Soprano", "9865877512", "ts", 1));
        a.add(new Accounts("Gustavo Fring", "9865877513", "gf", 0));
        a.add(new Accounts("Christopher Moltisanti", "9865877514", "cm", 0));
        a.add(new Accounts("Mark Grayson", "9865877515", "mg", 0));
        a.add(new Accounts("Skyler White", "9865877517", "sw", 1));
        a.add(new Accounts("Hank Schrader", "9865877518", "hs", 0));

        p.add(new Product(1, "Samsung M31", 16000.0, "Mobile", "RAM : 6GB, ROM : 128GB", 15.0, 400));
        p.add(new Product(2, "Apple iPhone 12", 79999.0, "Mobile", "RAM : 6GB, ROM : 256GB", 10.0, 200));
        p.add(new Product(3, "Dell XPS 13", 99999.0, "Laptop", "Intel Core i7, 16GB RAM, 512GB SSD", 12.0, 150));
        p.add(new Product(4, "Sony Bravia 55-inch", 54999.0, "TV", "4K UHD, Smart TV", 8.0, 100));
        p.add(new Product(5, "Nike Air Max", 6999.0, "Footwear", "Running Shoes", 20.0, 300));
        p.add(new Product(6, "Canon EOS Rebel T7i", 64999.0, "Camera", "24.2MP DSLR", 5.0, 50));
        Database database = new Database(a, p);
        saveObjectsToFile1(database.accounts, "accounts.ser");
        saveObjectsToFile2(database.products, "products.ser");
        System.out.println("Program ended.....");
    }
}
