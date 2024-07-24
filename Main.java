import java.util.*;
import java.io.*;
import mypackage.*;
class Use 
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

        ArrayList<Accounts> a = (ArrayList<Accounts>) loadObjectsFromFile1("accounts.ser");
        ArrayList<Product> p = (ArrayList<Product>) loadObjectsFromFile2("products.ser");
        Database database;

        if (a != null && p != null) {
            database = new Database(a, p);
            //System.out.println("Objects loaded successfully.");
        } 
        else 
        {
            System.out.println("Error loading objects.");
            database = null;
        }
       // Database database = new Database(a, p);
        Scanner obj = new Scanner(System.in);
        int flag=0;
        Accounts account = null;
        int login = 0;
        int c;
        while(true)
        {
            System.out.println("Press 1 for Login\nPress 2 for signup\nPress 3 for login as admin\nAny other number to exit");
            c = obj.nextInt();
            obj.nextLine();
            if(c==1)
            {
                while(true)
                {
                    System.out.print("Enter your mobile number : ");
                    String n;
                    try{
                        n = obj.nextLine();
                        if (n.length()!=10) {
                            throw new ValidMobileException("Mobile number format is not correct");
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                        break;
                    }
                    flag = 0;
                    for(Accounts q : a)
                    {
                        if(n.equals(q.getMobile()))
                        {
                            flag = 1;
                            account = q;
                        }
                    }
                    if(flag == 0)
                    {
                        System.out.println("No such account exist try sign up to add your account");
                        break;
                    }
                    else
                    {
                        System.out.print("Enter Password : ");
                        String pa = obj.nextLine();
                        if(account.getPassword().equals(pa))
                        {
                            database.user(account);
				            break;
                        }
                        else
                        {
                            System.out.println("Password is wrong Try again");
                            continue;
                        }
                    }
                }
            }
            else if(c==2)
            {
                System.out.print("Enter your Mobile number : ");
                String mobile;
                try{
                    mobile = obj.nextLine();
                    if (mobile.length()!=10) {
                        throw new ValidMobileException("Mobile number format is not correct");
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    break;
                }
                System.out.print("Enter your name : ");
                String name = obj.nextLine();
                System.out.print("Create Password : ");
                String pass = obj.nextLine();
                database.addAccounts(new Accounts(name, mobile, pass, c));
                System.out.println("Account created successfully Login to continue");
            }
            else if(c==3)
            {
                   database.adminFunctionality(database);
            }
            else
            {
                saveObjectsToFile1(database.accounts, "accounts.ser");
                saveObjectsToFile2(database.products, "products.ser");
                System.out.println("Program ended.....");
                break;
            }
        } 
    }
}
