using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Xml.Serialization;
using System.Xml;

namespace ATechTools.Xml
{
    public class XmlUtil
    {

        /// <summary>
        /// Method to convert a custom Object to XML string
        /// </summary>
        /// <param name="pObject">Object that is to be serialized to XML</param>
        /// <returns>XML string</returns>
        public static String SerializeObject(object pObject)
        {

            try
            {
                
                XmlSerializer serializer = new XmlSerializer(pObject.GetType() /*  typeof(pObject) */);
                StringWriter tw = new StringWriter(); //new StreamWriter(Server.MapPath("book1.xml"));
                serializer.Serialize(tw, pObject);

                return tw.ToString();

                //tw.Close(); 

                /*
                String XmlizedString = null;

                MemoryStream memoryStream = new MemoryStream();

                XmlSerializer xs = new XmlSerializer(pObject.GetType());

                // utf-8
                XmlTextWriter xmlTextWriter = new XmlTextWriter(memoryStream, Encoding.ASCII);
                xmlTextWriter.Formatting = Formatting.Indented;

                xs.Serialize(xmlTextWriter, pObject);
                memoryStream = (MemoryStream)xmlTextWriter.BaseStream;
                XmlizedString = UTF8ByteArrayToString(memoryStream.ToArray());

                return XmlizedString; */
            }
            catch (Exception e)
            {
                System.Console.WriteLine(e);
                return null;
            }
        }




        /// <summary>
        /// Method to reconstruct an Object from XML string
        /// </summary>
        /// <param name="pXmlizedString"></param>
        /// <returns></returns>
        public static Object DeserializeObject(String pXmlizedString, /*object obj*/ Type type)
        {
            XmlSerializer xs = new XmlSerializer(type); //   type.GetType());

            /*
            TextReader tr = new StreamReader(Server.MapPath("book1.xml"));
            Book b = (Book)serializer.Deserialize(tr);
            tr.Close();  */

            //MemoryStream memoryStream = new MemoryStream(StringToUTF8ByteArray(pXmlizedString));
            StringReader sr = new StringReader(pXmlizedString);
            // .UTF8
            //XmlTextWriter xmlTextWriter = new XmlTextWriter(memoryStream, Encoding.ASCII);

            return xs.Deserialize(sr);  //memoryStream);
        }


        /// <summary>
        /// To convert a Byte Array of Unicode values (UTF-8 encoded) to a complete String.
        /// </summary>
        /// <param name="characters">Unicode Byte Array to be converted to String</param>
        /// <returns>String converted from Unicode Byte Array</returns>
        public static String UTF8ByteArrayToString(Byte[] characters)
        {
            ASCIIEncoding encoding = new ASCIIEncoding();
            //  UTF8Encoding encoding = new UTF8Encoding();
            String constructedString = encoding.GetString(characters);
            return (constructedString);
            
        }


        /// <summary>
        /// Converts the String to UTF8 Byte array and is used in De serialization
        /// </summary>
        /// <param name="pXmlString"></param>
        /// <returns></returns>
        public static Byte[] StringToUTF8ByteArray(String pXmlString)
        {
            ASCIIEncoding encoding = new ASCIIEncoding();
            //UTF8Encoding encoding = new UTF8Encoding();
            Byte[] byteArray = encoding.GetBytes(pXmlString);
            return byteArray;
        } 

    }
}
