using System;

namespace c_sharp_mystuff
{
    internal class Program
    {
        static void Main(string[] args)
        {
            int abcsw = int.Parse("1234");
            Console.WriteLine(abcsw);
            Refs refs= new Refs();
            refs.normal(ref abcsw);
            Console.WriteLine(abcsw);


            A a = new A(12);
            B b = new B();
            A a2 = new B();

            a.M1();//A.M1
            a.M2();//A.M2
            b.M1();//B.M1
            b.M2();//B.M2
            a2.M1();//A.M1
            a2.M2();//B.M2


            ABC<int,int>jas= new ABC<int,int>();

            MathFunction f  = Math.Sin;
        }
    }
    class Exceptions
    {
        public void normal() {
            try{  }
            catch {}
            //oder
            try {}
            catch (NullReferenceException ex)  { }//oder Exception ex||

        }
    }

    class Eingaben
    {
        public void normal()
        {
            int num = int.Parse(Console.ReadLine());

            string str_1 = "asdf";//umwandeln
            try
            {
                int parse_1 = int.Parse(str_1);
                double zahl = double.Parse(str_1);
            }
            catch
            {
                Console.WriteLine("Konvertierung nicht möglich");
            }



        }
    }
    

    class Refs
    {
        public void normal(ref int a)//methode mit ref aufrufen  :: refs.normal(ref a)
        {
            a = 0;
        }
        public void normal2(out object a)//objekt zuweisen
        {
            a=new Eingaben();
        }
    }

    #region Vererbung 
    //unit 0x2
    public class A
    {
        private int n;
        public int ID { get; set; }

        // constructor (ctor) with argument, note "this"
        public A(int n) { this.n = n; }

        public void M1() { Console.WriteLine("A.M1"); }
        public virtual void M2() { Console.WriteLine("A.M2"); }
    }

    /// <summary>
    /// a sample sub-class 
    /// </summary>
    class B : A
    {
        // constructor w.o. arguments, calling base class cstr
        public B() : base(5) { }

        public new void M1() { Console.WriteLine("B.M1"); }

        public override void M2() { Console.WriteLine("B.M2"); }
    }

    abstract class x
    {
        public abstract void M1();
    }
    interface Ix { 
        void test();
    }
    class testInterface : x,Ix
    {
        public  void test() { Console.WriteLine(); }
        public override void M1() { }
    }
    //struct a,b
    //a=b neue copy :: ref bei funktionen notwenig(valuetype =struct
    #endregion

    #region erweiterungen und operatoren
    //unit 0x3
    //impliziert v=10  funktioniert :: expleziert v=(object)10
    class Vector2D
    {
        public int x, y;
        Vector2D(int x, int y) { }

        public static explicit operator Vector2D(int n)
        {
            return new Vector2D(n, n);
        }
        public static bool operator ==(Vector2D a, Vector2D b)
        {
            return (a.x == b.x && a.y == b.y);
        }

        public static bool operator !=(Vector2D a, Vector2D b)
        {
            return !(a == b);
        }
        public static Vector2D operator +(Vector2D a, Vector2D b)
        {
            return new Vector2D(a.x + b.x, a.y + b.y);
        }

        public static Vector2D operator *(int a, Vector2D b)
        {
            return new Vector2D(a * b.x, a * b.y);
        }

        public static Vector2D operator *(Vector2D b, int a)
        {
            return a * b;
        }
        public int this[string s]
        {
            get => (s == "0") ? x : y;      // get { return (s == "0") ? x : y; }
        }

        public int this[int index]
        {
            get => (index == 0) ? x : y;    // get { return (index == 0) ? x : y; }

            set
            {
                if (index == 0) x = value;
                else y = value;
            }
        }


    }


    public class MyClass//indicer oder indexer
    {
        private int[] _data = new int[10];

        public int this[int index]
        {
            get { return _data[index]; }
            set { _data[index] = value; }
        }
    }

    //method extention



    #endregion
    public class ABC<T,T2> //where T: A  T muss A sein
    {
        public T ID { get; set; }
    }
    // public static string asText(this ABC a, int n)
    // {
    //     return $"ID={a.ID}, n={n}";
    // }


    #region labda , delegate Type
    //unit 4
    //var rnd= getRandom()?.Next()??0 wenn nicht null Next() sonst 0

    //delegate = funktionsptr
    delegate double MathFunction(double x);/*
    MathFunction f = new MathFunction(Math.Sin);
    //oder f=Math.Sin
  
    var values = new double[] { 0, 1 * Math.PI, 2 * Math.PI };
    WriteValues(values, "sin", Math.Sin);
    WriteValues(values, "cos", Math.Cos);

    static void WriteValues(double[] values, string name, MathFunction f)
    {
        Console.WriteLine($"02) {name}:");
        foreach (var v in values)
            Console.WriteLine($"      {v} -> {f(v)}");
    */

    #endregion

    #region Enum Yield Linq
    //unit 5

    //yield return x     funktion wird "geparkt" ==lokale variablen beleiben
    #endregion





}
