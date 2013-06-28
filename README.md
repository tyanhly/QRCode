#QRCode

Grail plugin project - Create QRCode and can put your logo to QRCode image

The plugin support both of service and taglib. 

#About function:

- Create qrcode for a text
- Create qrcode for a contact - MECARD (Please see: http://www.nttdocomo.co.jp/english/service/developer/make/content/barcode/function/application/addressbook/index.html)
- Create qrcode follow google api (Please see: https://developers.google.com/chart/infographics/docs/qr_codes)

* And In each case you can put your logo in this qrcode image by link or by base64 string or object

#About technique:

- You can use service for customize you business functions. It supported you generate qrcode image for many types that you can do. etc.
   + You can write down you disk
   + You can get BufferedImage
   + You also can get Base64 string as well
- About logo image, It also support system path, URL or Base64 String as well,

#Installation
compile ":qr-code:0.1"

#Using

##User Information

<pre>

Map information = [:]
def contactInfo = "MECARD:N:Tung,Ly;ADR:76 9th Avenue, 4th Floor, New York, NY 10011;"\
                + "TEL:+84906667100;EMAIL:tyanhly@example.com;;"
information.put("chs", "chs=250x250")
information.put("cht", "cht=qr")
information.put("chl", "chl=" + URLEncoder.encode(contactInfo))
information.put("chld", "chld=H|1")
information.put("choe", "choe=UTF-8")
QRCodeService.createQRCode(information, "/local/logo.jpg", "/local", "qrcode.png")
</pre>

or

<pre>
def bytes = new URL("http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png").getBytes()
def data = bytes.encodeBase64().toString()
QRCodeService.createQRCodeAndLogoBase64(information, data, "/local", "google.png" )
</pre>

##Contact information

<pre>
def contactInfos = [:]
contactInfos.put("FIRSTNAME", "Tung")
contactInfos.put("LASTNAME", "Ly")
contactInfos.put("ADR", "124 Cao Xuan Duc P12 Q8 HCM")
contactInfos.put("TEL", "0906667100")
contactInfos.put("TEL-AV", "+84906667100")
contactInfos.put("EMAIL", "tyanhly@yahoo.com")
contactInfos.put("URL", "http://kiss-concept.com")
contactInfos.put("BDAY", "1985/01/01")

QRCodeService.createContactQRCode(contactInfos, \
    "http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png", \
    "/local", "qrcode2.png")
</pre>

or

<pre>
def abytes = new URL("http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png").getBytes()
        def adata = abytes.encodeBase64().toString()

        QRCodeService.createContactQRCodeAndLogoBase64(contactInfos, dataa, "/local", "ibm.png" )
</pre>

##TagLib using
<pre>
<code>
&lt;qrcode:text&gt;Hello QRCode Plugin for Grails&lt;/qrcode:text&gt;
&lt;qrcode:text
   logoLink=&quot;http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png&quot;&gt;
   Hello QRCode Plugin for Grails
&lt;/qrcode:text&gt;

&lt;qrcode:google information=&quot;${information}&quot; /&gt;
&lt;qrcode:google information=&quot;${information}&quot;
    logoLink=&quot;http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png&quot; /&gt;

&lt;qrcode:contact contactInfos=&quot;${contactInfos}&quot; /&gt;
&lt;qrcode:contact contactInfos=&quot;${contactInfos}&quot; 
    logoLink=&quot;http://upload.wikimedia.org/wikipedia/commons/5/51/Google.png&quot;/&gt;

&lt;qrcode:contact contactInfos=&quot;${contactInfos}&quot; 
    logoLink=&quot;~/Pictures/Google.png&quot;/&gt;
    
&lt;qrcode:contact contactInfos=&quot;${contactInfos}&quot; 
    logoBase64=&quot;AzdDBkkd...&quot;/&gt;
    
</code>
</pre>


