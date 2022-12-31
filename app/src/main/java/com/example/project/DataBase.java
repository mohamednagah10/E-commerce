package com.example.project;
import static com.example.project.products.id_pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DataBase extends SQLiteOpenHelper {

    private static String databasename = "E_Commerce";
    public SQLiteDatabase pro;

    Context con;
    public DataBase(Context context) { super(context, databasename, null, 1);

    con=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table Customers (CustId integer primary key autoincrement ,Custname text not null,Username text unique,"+
         "Password text not null , Gender text not null  ,Job text not null,Email text not null ,Birthday text  not null)");

        db.execSQL("create table Categories (Cat_ID integer primary key autoincrement ,CatName text )");

      db.execSQL("create table Orders (OrdId integer primary key autoincrement,OrdDate datetime not null,"+
         "Address text not null ,Cust_Id integer not null,Confirmed integer,FOREIGN KEY (Cust_Id) REFERENCES Customers(CustId))");


      db.execSQL("create table Products (ProId integer primary key autoincrement,ProName text unique ,Price integer not null,"+
                " Quantity integer not null ,CatID integer not null,FOREIGN KEY (CatID) REFERENCES Categories(Cat_ID))");

        db.execSQL("create table OrderDetails (Ord_Id integer ,Pro_Id integer ,Quantity integer not null ,"+
                "primary key  (Ord_Id,Pro_Id),FOREIGN KEY (Ord_Id) REFERENCES Orders(OrdId)," +
                "FOREIGN KEY (Pro_Id) REFERENCES Products(ProId) )" );

        ContentValues r = new ContentValues();

        r.put("CatName", "Classic");
        db.insert("Categories", null, r);

        r = new ContentValues();
        r.put("CatName", "Heels");
        db.insert("Categories", null, r);

        r = new ContentValues();
        r.put("CatName", "Boots");
        db.insert("Categories", null, r);


        //*************************************************************************************

        r = new ContentValues();
        r.put("ProName", "Oxford Men's shoes");
        r.put("Price", "350");
        r.put("Quantity", 3);

        r.put("CatID", "1");
        db.insert("Products", null, r);

        r = new ContentValues();
        r.put("ProName", "Genuine Leather");
        r.put("Price", 400);
        r.put("Quantity", 200);

        r.put("CatID", "1");


        db.insert("Products", null, r);

        r = new ContentValues();
        r.put("ProName", "Lace up Leather Shoes");
        r.put("Price", 600);
        r.put("Quantity", 150);

        r.put("CatID", "1");


        db.insert("Products", null, r);
        r = new ContentValues();

        r.put("ProName", "Medical Leather Shoes");
        r.put("Price", 300);
        r.put("Quantity", 170);
        r.put("CatID", "1");

        db.insert("Products", null, r);

////****************************************


        r = new ContentValues();
        r.put("ProName", "Oxford");
        r.put("Price", "300");
        r.put("Quantity", "170");
        r.put("CatID", "2");

        db.insert("Products", null, r);

        r = new ContentValues();

        r.put("ProName", "Stiletto");
        r.put("Price", "350");
        r.put("Quantity", "100");
        r.put("CatID", "2");


        db.insert("Products", null, r);



        r = new ContentValues();
        r.put("ProName", "Comma Heels");
        r.put("Price", "250");
        r.put("Quantity", "200");
        r.put("CatID", "2");

        db.insert("Products", null, r);


        r = new ContentValues();
        r.put("ProName", "Cork High Heels");
        r.put("Price", "400");
        r.put("Quantity", "300");
        r.put("CatID", "2");

        db.insert("Products", null, r);

//*************************************************************************
        r = new ContentValues();

        r.put("ProName", "Tall Boots");
        r.put("Price", "400");
        r.put("Quantity", "300");
        r.put("CatID", "3");

        db.insert("Products", null, r);



        r = new ContentValues();

        r.put("ProName", "Combat Boots");
        r.put("Price", "300");
        r.put("Quantity", "200");
        r.put("CatID", "3");


        db.insert("Products", null, r);
        r = new ContentValues();

        r.put("ProName", "Ankle Boots");
        r.put("Price", "350");
        r.put("Quantity", "100");
        r.put("CatID", "3");

        db.insert("Products", null, r);
        r = new ContentValues();

        r.put("ProName", "Chukka Boots");
        r.put("Price", "500");
        r.put("Quantity", "270");
        r.put("CatID", "3");

        db.insert("Products", null, r);

        r = new ContentValues();

        r.put("ProName", "Rain Boots");
        r.put("Price", "570");
        r.put("Quantity", "320");
        r.put("CatID", "3");


        db.insert("Products", null, r);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");

        onCreate(db);

    }

// public void CreateProduct ()
    {

        //pro = getWritableDatabase();


    //**************************************************

        //pro.close();
}

    public void Register (String C_name,String U_name,String Password,String Gender,String job,String Email,String Birthdate ){

        ContentValues row = new ContentValues();
    row.put("Custname",C_name);
    row.put("Username",U_name);
    row.put("Password",Password);
    row.put("Gender",Gender);
    row.put("Job",job);
    row.put("Email",Email);
    row.put("Birthday",Birthdate);
    pro=getWritableDatabase();

    pro.insert("Customers",null,row);
    pro.close();
    }

//check username
    public boolean userbefore (String u_name)
    {
        pro=getReadableDatabase();
        Cursor c= pro.rawQuery("select Username from Customers where Username='"+u_name+"'",null);
        c.moveToFirst();
        pro.close();

        if(c.getCount()==0)
            return false;

        return true;
    }

    String username ;
    static int  CustId ;

    //check username and pass
    public boolean Check_login (String u_name,String pass)
    {
    pro=getReadableDatabase();

    //String []arg ={u_name,pass};

    Cursor c= pro.rawQuery("select CustId ,Username , Password from Customers where Username='"+u_name+"' " +
            "and Password ='"+pass+"'",null);
    c.moveToFirst();
    pro.close();

    if(c.getCount()==0)
            return false;
        username = u_name;
        CustId = Integer.parseInt(c.getString(0));
    return true;
    }

//update pass by username
    public boolean Update_Password (String u_name,String pass){
        pro=getWritableDatabase();

        String []arg ={u_name};
        Cursor c= pro.rawQuery("select Username from Customers where Username like ?",arg);
        c.moveToFirst();
        if(c.getCount()!=0)
        {
        ContentValues r = new ContentValues();
        r.put("Password", pass);
        pro.update("Customers",r,"Username like ?",new String[] {u_name});
        pro.close();
        return true;

        }
        return false;
    }


//info of products at category
    public Cursor getAllProducts(int id) {
        pro = getReadableDatabase();

        String [] arg ={"ProName"};
        Cursor c = pro.rawQuery("select * from Products where CatID = "+id+"", null);
        int x = c.getCount();

        if (x>0)
            c.moveToFirst();
        pro.close();
        return c;
    }

    // product of Search
    public Cursor get_product(String name) {
        pro=getReadableDatabase();
        String [] arg ={"%"+name+"%"};
        Cursor c = pro.rawQuery("select ProName from Products where ProName like ?",arg);
        if (c !=null)
            c.moveToFirst();
        pro.close();
        return c;

    }

//info products
    public product cart (String name) {
        Cursor cursor;
        pro=getReadableDatabase();

        String [] arg ={name};

        cursor = pro.rawQuery("select * from Products where ProName like ?",arg);

        if (cursor !=null)
            cursor.moveToFirst();
        int x = Integer.parseInt(cursor.getString(0));

        product p = new product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                Integer.parseInt( cursor.getString(2)),Integer.parseInt( cursor.getString(3)),
                Integer.parseInt( cursor.getString(4)));

        pro.close();

        return p;

    }


    //check cust submit
    public boolean CheckISOreded (){
        pro=getReadableDatabase();

        Cursor c = pro.rawQuery("select * from Orders where Cust_Id ='"+CustId+"' ",null);

          if(c.getCount() > 0)
        {
            c.moveToFirst();

            // if order not confirmed
           if(Integer.parseInt(c.getString(4) )== 0)
                return false;
        }

        pro.close();
    return true;
    }

//table order
    public void Insert_Order (String Address ){

        ContentValues row = new ContentValues();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        row.put("OrdDate",dtf.format(now));
        row.put("Address",Address);
        row.put("Cust_Id",CustId);
        row.put("Confirmed",0);

        pro = getWritableDatabase();
        pro.insert("Orders",null,row);
        pro.close();

    }

    static int OrdId;

    //get OrdOD which not submit
    public int to_ID (int Customer_ID)
    {
        ContentValues row = new ContentValues();
        pro = getReadableDatabase();

        Cursor c = pro.rawQuery("select OrdId from Orders where Cust_Id ='"+Customer_ID+"'and Confirmed =0",null);
        c.moveToFirst();
        OrdId = Integer.parseInt(c.getString(0));
        pro.close();
        return OrdId;
    }

//table Order_Details
    public void Insert_Order_Details (int Pro_Id, int Quantity){
        ContentValues row = new ContentValues();
        OrdId = to_ID(CustId);
        row.put("Ord_Id",OrdId);
        row.put("Pro_Id",Pro_Id);
        row.put("Quantity",Quantity);
        pro = getWritableDatabase();
        pro.insert("OrderDetails",null,row);
        pro.close();

    }


//check product at order details for update quantity
        public boolean CheckISOreded_Details (int proID) {
            pro=getReadableDatabase();

            Cursor c = pro.rawQuery("select * from OrderDetails where Ord_Id ='"+OrdId+"' and Pro_Id ='"+proID+"'",null);
            c.moveToFirst();

            if (c.getCount()>0)
                return true;
            pro.close();
            return false;

        }

    public Cursor Get_Quantaty (int orderID){
        pro = getReadableDatabase();
        Cursor cursor;
        cursor = pro.rawQuery("select * from OrderDetails  where Ord_Id ='"+orderID+"'",null);
        cursor.moveToFirst();
        pro.close();
        return cursor;
    }



    public int getQuantiy (int proID,int ordId) {
        pro=getReadableDatabase();
        Cursor c = pro.rawQuery("select Quantity from OrderDetails where Ord_Id ='"+OrdId+"' and Pro_Id ='"+proID+"'",null);
        c.moveToFirst();
        pro.close();
        return Integer.parseInt(c.getString(0));

    }
//update quantity at order and products
    public void update_order_details (int proID,int quanDetails) {

       // OrdId = to_ID(CustId);
        int quantity = getQuantiy(proID,OrdId);
        int quantity_product=0;
        quantity+= quanDetails-quantity;

        pro=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("Quantity",quantity);
        pro.update("OrderDetails",row,"Ord_Id = " + OrdId + " and Pro_Id = " +proID ,null);
        pro=getReadableDatabase();

        Cursor c = pro.rawQuery("select Quantity from Products where ProId ='"+proID+"'",null);
        c.moveToFirst();
        quantity_product= c.getInt(0)- quantity;
        pro=getWritableDatabase();
        row.put("Quantity",quantity_product);
        pro.update("Products",row," ProId = " +proID ,null);

        pro.close();
          }

    public Cursor show_order (){

        ContentValues row = new ContentValues();
        pro = getReadableDatabase();

Cursor c = pro.rawQuery("select a.ProName , a.Price , b.Quantity from  Products a , OrderDetails b , Orders c" +
 " where  a.ProId  = b.Pro_Id and  b.Ord_Id   ='"+OrdId+"' and c.OrdId  = b.Ord_Id  and c.Cust_Id ='"+CustId+"' and c.Confirmed = 0 ",null);
       int x=c.getCount();
        if (c !=null)
        {
            c.moveToFirst();
        }

    return c;
    }


    public void Delete_product (int ProId){
        ContentValues row = new ContentValues();
        pro = getReadableDatabase();
        Cursor c = pro.rawQuery("select Quantity from OrderDetails  where Pro_Id ='"+ProId+"'",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            int quantity_product = Integer.parseInt(c.getString(0));

            pro = getWritableDatabase();
            pro.delete("OrderDetails", "Pro_Id = '" + ProId + "' and Ord_Id = '" + OrdId + "'", null);

            pro = getReadableDatabase();
            c = pro.rawQuery("select Quantity from Products where ProId ='" + ProId + "'", null);
            c.moveToFirst();
            quantity_product = c.getInt(0) + quantity_product;
            pro = getWritableDatabase();
            row.put("Quantity", quantity_product);
            pro.update("Products", row, " ProId = " + ProId, null);

               }
        pro.close();

    }

    public void ConfirmOrder () {

        pro=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("Confirmed",1);
        pro.update("Orders",row,"OrdId = " + OrdId ,null);
        pro.close();
    }
    public Cursor Search_product (String name) {
        Cursor cursor;
        pro=getReadableDatabase();
        String [] arg ={name};
        cursor = pro.rawQuery("select * from Products where ProName like ?",arg);
        if (cursor !=null)
            cursor.moveToFirst();

        int x = Integer.parseInt(cursor.getString(2));

        pro.close();
        return cursor;

    }


}
