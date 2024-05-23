package get.newmaps.notes;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLControl {
    class Contains{
        private final File patchXML;
        public Contains(File patch){
            this.patchXML=patch;
        }
        public boolean contains(Node uno) throws Exception {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document=documentBuilder.parse(patchXML);
            Node root=document.getDocumentElement();
            NodeList nl=root.getChildNodes();
            for(int i=0;i<nl.getLength();i++){
                Node nod=nl.item(i);
                if(nod.isEqualNode(uno)){
                    return true;
                }
            }
            return false;
        }
        public boolean contains(String strit,String nomerHome,String KolvoApartamants,String str) throws Exception{
            Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element RootHome=document.createElement("HomeInfo");
            Element stritElement=document.createElement("Strit");
            stritElement.setTextContent(strit);
            Element strE=document.createElement("STR");
            strE.setTextContent(str);
            Element homeElement=document.createElement("HOMENomer");
            homeElement.setTextContent(nomerHome);
            Element kolvoApart = document.createElement("KolvoApart");
            kolvoApart.setTextContent(KolvoApartamants);
            RootHome.appendChild(stritElement);
            RootHome.appendChild(homeElement);
            RootHome.appendChild(kolvoApart);
            RootHome.appendChild(strE);
            return contains(RootHome);
        }
        public boolean contains(Document doc, Node uno){
            Node root=doc.getDocumentElement();
            NodeList nl=root.getChildNodes();
            for(int i=0;i<nl.getLength();i++){
                Node nod=nl.item(i);
                if(nod.isEqualNode(uno)){
                    return true;
                }
            }
            return false;
        }
        public int getLength(File patchXML){
            try {
                Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(patchXML);
                return document.getDocumentElement().getChildNodes().getLength();
            } catch (Exception e) {
                return 0;
            }
        }

        public boolean contains(Document doc, String strit, String nomerHome, String KolvoApartamants, String str){
            Element RootHome=doc.createElement("HomeInfo");
            Element stritElement=doc.createElement("Strit");
            stritElement.setTextContent(strit);
            Element strE=doc.createElement("STR");
            strE.setTextContent(str);
            Element homeElement=doc.createElement("HOMENomer");
            homeElement.setTextContent(nomerHome);
            Element kolvoApart = doc.createElement("KolvoApart");
            kolvoApart.setTextContent(KolvoApartamants);
            Element newNameE = doc.createElement("NewName");
            newNameE.setTextContent(new Formater().getFormateString(strit+nomerHome));
            RootHome.appendChild(stritElement);
            RootHome.appendChild(homeElement);
            RootHome.appendChild(kolvoApart);
            RootHome.appendChild(strE);
            RootHome.appendChild(newNameE);
            Node root=doc.getDocumentElement();
            NodeList nl=root.getChildNodes();
            for(int i=0;i<nl.getLength();i++){
                Node nod=nl.item(i);
                if(nod.isEqualNode(RootHome)){
                    return true;
                }
            }
            return false;
        }
    }
    static class Reader{
        private volatile ArrayList<Home> ListHome;
        private final File patchXML;
        private final byte zone;
        public Reader(File patchXML, byte zone){
            this.patchXML=patchXML;
            this.zone = zone;
        }
        public ArrayList<Home> getListHome(){
            try {
                Read();
            } catch (FileNotFoundException e){
                e.printStackTrace();
                return new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
            return ListHome;
        }
        public synchronized void OldRead(NodeList nll, int i) throws Exception {
            Home home=new Home();
            home.id=i;
            home.strit=nll.item(0).getTextContent();
            home.nomerHome=nll.item(1).getTextContent();
            home.nomerApar=nll.item(2).getTextContent();
            home.str=nll.item(3).getTextContent();
            home.newName=nll.item(4).getTextContent();
            ListHome.add(home);
        }

public static Context context;
        public synchronized void Read() throws Exception {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SoftReference<Document> document;
            document=new SoftReference<Document>(documentBuilder.parse(patchXML));
            Node root=document.get().getDocumentElement();
            NodeList nl=root.getChildNodes();
            ListHome=new ArrayList<>(nl.getLength());
            for(int i=0;i<nl.getLength();i++){
                int y=i;
                NodeList nll=nl.item(y).getChildNodes();
                try{
                    Home home=new Home();
                    home.id=y;
                    home.strit=nll.item(0).getTextContent();
                    home.nomerHome=nll.item(1).getTextContent();
                    home.nomerApar=nll.item(2).getTextContent();
                    home.str=nll.item(3).getTextContent();
                    home.newName=nll.item(4).getTextContent();
                    home.X=nll.item(5).getTextContent();
                    home.Y=nll.item(6).getTextContent();
                    home.zone=zone;
                    ListHome.add(home);
                }catch(Exception e){
                    OldRead(nll,y);
                    e.printStackTrace();
                    return;
                }
            }
        }
        static class Home{
            int id=-1;
            String strit="";
            String newName="";
            String nomerHome="";
            String nomerApar="";
            String str="";
            byte zone;
            String X="";
            String Y="";
            public Home(String strit){
                this.strit=strit;
            }
            public Home(){
            }
        }
    }
    static class Writer{
        public void writeDocument(Document document, File patchXML) throws TransformerFactoryConfigurationError {
            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(document);
                FileOutputStream fos = new FileOutputStream(patchXML);
                StreamResult result = new StreamResult(fos);
                tr.transform(source, result);
            } catch (TransformerException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
