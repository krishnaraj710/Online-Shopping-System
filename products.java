import java.util.*;
import java.io.*;
import java.io.*;
interface Item
{
  public double getPrice();
  public void order();
  public int getID();
  public String getName();
  public double getOffer();
  public void updateRating(double r);
  public int getSL();	
}

class Product implements Item, Serializable
{
  private int id;
  private String name;
  private double price;
  private String type;
  private String description;
  private double offer;
  private int stockLeft;
  private int stockSold;
  private double rating;

  public Product(int i, String n, double p, String t, String des, double o, int sl)
  {
    id = i;
    name = n;
    price = p;
    type = t;
    description = des;
    offer = o;
    stockLeft = sl;
    stockSold = 0;
    rating = 0;
  }

  public int getID()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public double getPrice()
  {
    return price;
  }

  public double getOffer()
  {
    return offer;
  }
    public String getType()
    {
        return type;
    }
  public int getSL()
  {
    return stockLeft;
  }
  public int getSS()
  {
    return stockSold;
  }
  public void addStock(int a)
  {
    stockLeft += a;
  }
  public void updateRating(double r)
  {
    rating = ((rating * stockSold-1)+r)/stockSold;
  }
  public void order()
  {
    stockSold++;
  }
  public void editOffer(double o)
  {
    offer = o;
  }
    public void details()
    {
        System.out.println(getName()+"\nPrice : "+getPrice()+"\nID : "+getID()+"\n"+description);
    }
}


abstract class Pack implements Serializable
{
  Product a;
  int quantity;

  public abstract double cal();
}

class Normal extends Pack
{
  Normal(Product i, int q)
  {
    a = i;
    quantity = q;
  }
  public double cal()
  {
    return a.getPrice()*quantity;
  } 
}

class Prime extends Pack
{
  Prime(Product i, int q)
  {
    a = i;
    quantity = q;
  }
  public double cal()
  {
    return (a.getPrice()-(a.getPrice()*a.getOffer()/100))*quantity;
  }
}

class Cart implements Serializable
{
  ArrayList<Pack> list;
    Cart()
    {
        list = new ArrayList<>();
    }
    public boolean isempty()
    {
        if(list.isEmpty())
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
  public void addItem(Pack a)
  {
    list.add(a);
  }
  public void removeItem(Pack a)
  {
    list.remove(a);
  }
  public double caltotal()
  {
    double d = 0;
    for(Pack i:list)
    {
      d += i.cal();
    }
    return d;
  }
  public void view()
  {
    for(Pack i:list)
    {
      System.out.println(i.a.getName()+" "+i.a.getPrice()+"*"+i.quantity+" "+i.cal());
    }
    System.out.println("Total : " + caltotal());
  }
}

class Order implements Serializable {
    int id;
    Cart cart;
    int otp;
    Date orderTime;
    Date deliveryTime;
    String address;
    int status;
    String complain;

    Order(Cart c, String a)
  {
        cart = c;
        address = a;
        status = 0;
        orderTime = new java.util.Date();
        deliveryTime = new Date(orderTime.getTime() + 14 * 24 * 60 * 60 * 1000);
    Random random = new Random();
        id = random.nextInt(1000000);
        otp = random.nextInt(10000);
    }

    public void changeStatus(int a)
  {
        status = a;
    }

    public void rating()
  {
        System.out.println("Enter ratings");
        Scanner obj = new Scanner(System.in);
        for (Pack i : cart.list)
    {
            System.out.print(i.a.getName() + " : ");
      double d = obj.nextDouble();
            i.a.updateRating(d);
        }
    }
  public void complain(String s)
  {
    complain = s;
  }
  public int getID()
  {
    return id;
  }

  public int getOTP()
  {
    return otp;
  }
  public void details()
  {
    System.out.println("ID : "+getID());
    cart.view();
    System.out.println("Ordered time : " + orderTime);
    System.out.println("Delivery time : " + deliveryTime);
    System.out.println("OTP : "+ otp);
    System.out.println("Address :\n"+ address);
    if(status==0)
    {
      System.out.println("Status : Not Delivered yet");
    }
    else if(status == 0 && deliveryTime.compareTo(new java.util.Date())<=0)
    {
      System.out.println("Status : Delivered not rated");
      status=1;
    }
    else if(status == 1)
    {
      System.out.println("Status : Delivered not rated");
    }
    else if(status == 2)
    {
      System.out.println("Status : Delivered and rated");
    }
    else if(status == 3)
    {
      System.out.println("Status : Returned");
    }
    else if(status == 4)
    {
      System.out.println("Status : Canceled");
    }
  }
  public void updateSL()
  {
    for(Pack p : cart.list)
    {
        p.a.addStock(-(p.quantity));
    }
  }
  public void returnSL()
  {
    for(Pack p : cart.list)
    {
        p.a.addStock(p.quantity);
    }
  }
}

class Accounts implements Serializable
{
    public String name,mobile,password;
    public ArrayList<Order> activeOrders, completedOrders;
    public int prime;


    public Accounts(String name,String mobile,String password, int prime)
  {
        this.name = name;
        this.mobile = mobile ;
        this.password  = password;
        /*this.activeOrder = activeOrder;
        this.completeOrder = completeOrder;*/
        this.prime = prime;
        activeOrders = new ArrayList<>();
        //completedOrders = new ArrayList<>();
    }

    public void upgradePrime()
    {
        prime =1;
    }

    public void cancelPrime()
    {
        prime =0;
    }

    public String getName()
  {
        return name;
    }

     public String getMobile()
  {
        return mobile;
    }

     public String getPassword()
  {
       return password;
    }

    public int getPrime()
    {
        return prime;
    }

    public void placeOrder(Order o)
    {
        activeOrders.add(o);
        o.updateSL();
        //o.details();
    }

    public void returnOrder(int id)
    {

    int a =0;
    for(Order o : activeOrders)
    {
      if(o.status == 0 && o.deliveryTime.compareTo(new java.util.Date())<=0)
      {
      //System.out.println("Status : Delivered not rated");
        o.status=1;
      }
      if(o.getID()==id && o.status!=0)
      {
        o.changeStatus(3);
        a=1;
        System.out.println("Order is returned");
        o.returnSL();
      }
    }
    if(a==0)
    {
      System.out.println("Not found");
    }
    }

  public void cancelOrder(int id) {
        int a = 0;
        for (Order o : activeOrders) {
            if (o.status == 0 && o.deliveryTime.compareTo(new java.util.Date()) <= 0) {
                o.status = 1;
            }
            if (o.getID() == id && o.status == 0) {
                o.changeStatus(4);
                System.out.println("Order is cancelled");
                a = 1;
                o.returnSL();
            }
        }
        if (a == 0) {
            System.out.println("Order not found");
        }
    }

    public void viewOrder(int id) {
        int found = 0;
        System.out.println(" Your order is ");
        for (Order o : activeOrders) {
            if (o.status == 0 && o.deliveryTime.compareTo(new java.util.Date()) <= 0) {
                o.status = 1;
            }
            if (o.getID() == id) {
                o.details();
                found = 1;
            }
        }
        if (found == 0) {
            System.out.println("Order not found");
        }
    }

    public void viewActive() {
        int found = 0;
        System.out.println("Active orders are ");
        for (Order o : activeOrders) {
            if (o.status == 0 && o.deliveryTime.compareTo(new java.util.Date()) <= 0) {
                o.status = 1;
            }
            if (o.status == 0) {
                o.details();
                found = 1;
            }
        }
        if (found == 0) {
            System.out.println("No active orders found");
        }
    }

    public void viewCompleted() {
        int found = 0;
        System.out.println("Completed orders are ");
        for (Order o : activeOrders) {
            if (o.status == 0 && o.deliveryTime.compareTo(new java.util.Date()) <= 0) {
                o.status = 1;
            }
            if (o.status != 0) {
                o.details();
                found = 1;
            }
        }
        if (found == 0) {
            System.out.println("No completed orders found");
        }
    }

    public void rateOrders(int id) {
        int found = 0;
        for (Order o : activeOrders) {
            if (o.status == 0 && o.deliveryTime.compareTo(new java.util.Date()) <= 0) {
                o.status = 1;
            }
            if (o.status == 1) {
                o.rating();
                o.changeStatus(2);
                found = 1;
            }
        }
        if (found == 0) {
            System.out.println("No orders to rate");
        }
    }
}
class Database{
     public ArrayList<Accounts> accounts;
     public ArrayList<Product> products;

    public Database(ArrayList<Accounts> accounts,ArrayList<Product> products){
        this.accounts = accounts;
        this.products = products ;

    }


 public void adminFunctionality(Database database) {
    Scanner obj = new Scanner(System.in);
   
    System.out.print("Enter Admin Password: ");
    String adminPassword = obj.nextLine();
    if (adminPassword.equals("password")) {
   
       System.out.print("Admin Login successful.\n ");
       while (true) {
          System.out.println("Admin Functionality:");
          System.out.println("1. Add Product");
          System.out.println("2. View Products");
          System.out.println("3. View User Accounts");
          System.out.println("4. Update stock");
          System.out.println("5. Edit offers");
          System.out.println("6. Exit Admin Mode");

          int choice = obj.nextInt();
          obj.nextLine();

          switch (choice) {
                case 1:
                  // Admin adds a new product
                  System.out.print("Enter Product ID: ");
                  int productId = obj.nextInt();
                  obj.nextLine();

                  System.out.print("Enter Product Name: ");
                  String productName = obj.nextLine();

                  System.out.print("Enter Product Price: ");
                  double productPrice = obj.nextDouble();
                  obj.nextLine();

                  System.out.print("Enter Product Type: ");
                  String productType = obj.nextLine();

                  System.out.print("Enter Product Description: ");
                  String productDescription = obj.nextLine();

                  System.out.print("Enter Product Offer: ");
                  double productOffer = obj.nextDouble();
                  obj.nextLine();

                  System.out.print("Enter Initial Stock Left: ");
                  int stockLeft = obj.nextInt();
                  obj.nextLine();

                  database.addProducts(new Product(productId, productName, productPrice, productType,
                          productDescription, productOffer, stockLeft));

                  System.out.println("Product added successfully.");
                  break;

                case 2:
                  // Admin views all products
                  System.out.println("All Products:");
                  for (Product product : database.products) {
                      product.details();
                      System.out.println("---------------");
                  }
                  break;

                case 3:
                  // Admin views all user accounts
                  System.out.println("All User Accounts:");
                  for (Accounts user : database.accounts) {
                      System.out.println("Name: " + user.getName());
                      System.out.println("Mobile: " + user.getMobile());
                      System.out.println("Prime Membership: " + (user.getPrime() == 1 ? "Yes" : "No"));
                      System.out.println("---------------");
                  }
                  break;
                
                case 4:
                  System.out.println("Enter the id of the product : ");
                  int i = obj.nextInt();
                  int y = 0;
                  for(Product p : products)
                  {
                    if(p.getID()==i)
                    {  
                        p.details();
                        y=1;
                        System.out.print("Enter the stock to be added : ");
                        int x = obj.nextInt();
                        p.addStock(x);
                        System.out.println("Stock added successfully\n Updated Stock : "+ p.getSL());
                    }
                    
                  }
                  if(y==0)
                  {
                        System.out.println("No product exist please check and try again");
                  }
                  break;
                
                case 5:
                  System.out.println("Enter the id of the product : ");
                  int i1 = obj.nextInt();
                  int y1 = 0;
                  for(Product p : products)
                  {
                    if(p.getID()==i1)
                    {  
                        p.details();
                        y1=1;
                        System.out.print("Enter the updated offer : ");
                        double x = obj.nextDouble();
                        p.editOffer(x);
                        System.out.println("Offer updated successfully");
                    }
                  }
                  if(y1==0)
                    {
                        System.out.println("No product exist please check and try again");
                    }
                  break;    
              
                case 6:
                  // Exit admin mode
                  System.out.println("Exiting Admin Mode.");
                  return;

              default:
                  System.out.println("Invalid choice. Try again.");
                  break;
          }
      }
  }
  else{
      System.out.println("Admin Login Failed.\n");
  }
}

    public void addAccounts(Accounts a){
        accounts.add(a);
    }
    public void addProducts(Product p){
        products.add(p);
    }
    public void searchbytype(String n)
    {
        for(Product p : products)
        {
            if(p.getType().equalsIgnoreCase(n))
            {
                p.details();
            }
        }
    }
    public void searchbyname(String n)
    {
        for(Product p : products)
        {
            if(p.getName().equalsIgnoreCase(n))
            {
                p.details();
            }
        }
    }

    public void user(Accounts a)
    {
        Scanner obj = new Scanner(System.in);
        while(true)
        {
            System.out.println("Press 1 to order\nPress 2 to return your order\nPress 3 to rate your orders\nPress 4 to cancel the orders\nPress 5 to view your orders\nPress 6 to view membership\nAny other to exit");
            int c = obj.nextInt();
            obj.nextLine();
            Cart cart = new Cart();
            if(c==1)
            {
                //Cart cart = new Cart();
                while(true)
                {
                    //Cart cart = new Cart();
                    System.out.println("Press 1 to add to cart\nPress 2 to view cart\nPress 3 to remove from cart\nPress 4 to Finalise");
                    int cc = obj.nextInt();
                    obj.nextLine();
                    if(cc==1)
                    { 
                        System.out.println("Press 1 to search by name\nPress 2 to search by type\nPress 3 to order\nPress any other number to exit");
                        int c1 = obj.nextInt();
                        Pack p = null;
                        obj.nextLine();
                        if(c1 == 1)
                        {
                            System.out.print("Enter the name : ");
                            String n = obj.nextLine();
                            searchbyname(n);
                        } 
                        else if(c1==2)
                        {
                            System.out.print("Enter the type : ");
                            String t = obj.nextLine();
                            searchbytype(t);
                        }
                        else if(c1==3)
                        {
                            int flag =0;
                            System.out.println("Enter the ID of the product : ");
                            int i = obj.nextInt();
                            obj.nextLine();
                            for(Product pr : products)
                            {
                                if(pr.getID()==i)
                                {
                                    flag =1;
                                    System.out.print("Enter the quantity : ");
                                    int q = obj.nextInt();
                                    obj.nextLine();
                                    if(a.getPrime()==0)
                                    {
                                        p = new Normal(pr, q);
                                        break;
                                        //System.out.println("!");
                                    }
                                    else
                                    {
                                        p = new Prime(pr, q);
                                        break;
                                        //System.out.println("!");
                                    }
                                }
                            }
                            if(flag==0 || p == null)
                            {
                                System.out.println("No such product exist");
                            }
                            else
                            {
                                cart.addItem(p);
                                cart.view();
                                //System.out.println("%");
                            }
                        }
                    }
                    else if(cc==2)
                    {
                        if(!cart.isempty())
                        {
                            cart.view();
                        }
                        else
                        {
                            System.out.println("Cart is empty");
                        }
                    }
                    else if(cc==3)
                    {
                        System.out.println("Enter the ID of the item to be removed");
                        int id = obj.nextInt();
                        obj.nextLine();
                        for(Pack i: cart.list)
                        {
                            if(i.a.getID()==id)
                            {
                                cart.removeItem(i);
                            }
                        }
                    }
                    else if(cc==4 && cart != null)
                    {
                        System.out.print("Enter the shipping address : ");
                        String add=obj.nextLine();
                        Order o =new Order(cart, add);
                        a.placeOrder(o);
                        System.out.println("Order placed Successfully");
                        break;
                    }
                }

            }
            else if(c==2)
            {
                System.out.print("Enter the order id : ");
                int i = obj.nextInt();
                obj.nextLine();
                a.returnOrder(i);
            }
            else if(c==3)
            {
                System.out.print("Enter the order id : ");
                int i = obj.nextInt();
                obj.nextLine();
                a.rateOrders(i);
            }
            else if(c==4)
            {
                System.out.print("Enter the order id : ");
                int i = obj.nextInt();
                obj.nextLine();
                a.cancelOrder(i);
            }
            else if(c==5)
            {
                System.out.println("Enter 1 to search\nEnter 2 to view all active order\nEnter 3 to view all completed order");
                int c2 = obj.nextInt();
                obj.nextLine();
                if(c2==1)
                {
                    System.out.print("Enter the order id : ");
                    int i = obj.nextInt();
                    obj.nextLine();
                    a.viewOrder(i);
                }
                else if(c2==2)
                {
                    a.viewActive();
                }
                else if(c2==3)
                {
                    a.viewCompleted();
                }
            }
            else if(c==6)
            {
                if(a.getPrime()==0)
                {
                    System.out.println("Enter 1 to upgrade to prime with many offers with a monthy subscription : ");
                    int i = obj.nextInt();
                    if(i==1)
                    {
                        a.upgradePrime();
                        System.out.println("You are a prime member now");
                    }
                    else{
                        System.out.println("Exit");
                    }
                }
                else
                {
                    System.out.println("Enter 1 to cancel prime membership : ");
                    int i = obj.nextInt();
                    if(i==1)
                    {
                        a.cancelPrime();
                        System.out.println("Membership cancelled");
                    }
                    else{
                        System.out.println("Exit");
                    }
                }
            }
            else
            {
                System.out.println("Logging out");
                break;
            }
        }
    }   
}



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
                    String n = obj.nextLine();
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
                String mobile = obj.nextLine();
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
