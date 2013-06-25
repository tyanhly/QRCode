package grails.plugins.qrcode

import java.awt.Graphics
import java.awt.RenderingHints
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.apache.commons.validator.UrlValidator

class QRCodeService {

    static transactional = false

    private String tmpQrCodeFileName = "tmpQrCode.png"

    /**
     * Create QRCode Image and combine logo with this image
     *
     * @param information
     * @param logoPath
     * @return
     */
    def createQRCode(Map<String, String> information, String logoPath, String outputDir = "/tmp", String outputFileName = "QRCode.png"){
        def tmpQrCodeImage = createQRCodeByGoogle(information, outputDir + "/" + tmpQrCodeFileName)
        if(tmpQrCodeImage != null){
            mergeLogo(tmpQrCodeImage, logoPath, outputDir, outputFileName)
        }
    }

    /**
     * create QRCode Image for contact information and combine logo with this image
     *
     * @param contactInfos
     * @param logoPath
     * @return
     */
    def createContactQRCode(Map<String, String> contactInfos, String logoPath, String outputDir = "/tmp", String outputFileName = "QRCode.png"){
        def tmpQrCodeImage = createContactQRCodeByGoogle(contactInfos, outputDir + "/" + tmpQrCodeFileName)
        if(tmpQrCodeImage != null){
            mergeLogo(tmpQrCodeImage, logoPath, outputDir, outputFileName)
        }
    }

    /**
     * Create QRCode Image and combine logo with this image
     *
     * @param information
     * @param logoPath
     * @return
     */
    def createQRCodeAndLogoBase64(Map<String, String> information, String logoBase64, String outputDir = "/tmp", String outputFileName = "QRCode.png"){
        def tmpQrCodeImage = createQRCodeByGoogle(information, outputDir + "/" + tmpQrCodeFileName)
        if(tmpQrCodeImage != null){
            mergeLogoBase64(tmpQrCodeImage, logoBase64, outputDir, outputFileName)
        }
    }

    /**
     * Create QRCode Image and combine logo with this image
     *
     * @param information
     * @param logoPath
     * @return
     */
    def createContactQRCodeAndLogoBase64(Map<String, String> contactInfos, String logoBase64, String outputDir = "/tmp", String outputFileName = "QRCode.png"){
        def tmpQrCodeImage = createContactQRCodeByGoogle(contactInfos, outputDir + "/" + tmpQrCodeFileName)
        if(tmpQrCodeImage != null){
            mergeLogoBase64(tmpQrCodeImage, logoBase64, outputDir, outputFileName)
        }
    }

    /**
     * create QRCode Image for contact information. Using google API
     *
     * @refer http://code.google.com/p/zxing/wiki/BarcodeContents
     *        http://www.nttdocomo.co.jp/english/service/developer/make/content/barcode/function/application/addressbook/index.html
     * @param contactInfos
     * @param outputPath
     * @return
     */
    def createContactQRCodeByGoogle(Map<String, String> contactInfos, String outputPath){

        def firstname = contactInfos.containsKey("FIRSTNAME")?contactInfos.get("FIRSTNAME"): "Anonymous"
        def lastname = contactInfos.containsKey("LASTNAME")?contactInfos.get("LASTNAME"): ""

        String contactInfo = "MECARD:N:" + firstname + "," + lastname + ";"
        contactInfo += contactInfos.containsKey("ADR")?"ADR:" + contactInfos.get("ADR") + ";": ""
        contactInfo += contactInfos.containsKey("TEL")?"TEL:" + contactInfos.get("TEL") + ";": ""
        contactInfo += contactInfos.containsKey("TEL-AV")?"TEL-AV:" + contactInfos.get("TEL-AV") + ";": ""
        contactInfo += contactInfos.containsKey("EMAIL")?"EMAIL:" + contactInfos.get("EMAIL") + ";": ""
        contactInfo += contactInfos.containsKey("SOUND")?"SOUND:" + contactInfos.get("SOUND") + ";": ""
        contactInfo += contactInfos.containsKey("NOTE")?"NOTE:" + contactInfos.get("NOTE") + ";": ""
        contactInfo += contactInfos.containsKey("URL")?"URL:" + contactInfos.get("URL") + ";": ""
        contactInfo += contactInfos.containsKey("BDAY")?"BDAY" + contactInfos.get("BDAY") + ";": ""
        contactInfo += contactInfos.containsKey("NICKNAME")?"NICKNAME:" + contactInfos.get("NICKNAME") + ";": ""

        contactInfo += ";"

        Map information = [
            chs: "chs=250x250",
            cht: "cht=qr",
            chl: "chl=" + URLEncoder.encode(contactInfo),
            chld: "chld=H|1",
            choe: "choe=UTF-8"]
        createQRCodeByGoogle(information, outputPath)
    }

    /**
     *
     * @param information
     * @param outputPath
     * @return
     */
    def createQRCodeByGoogle(Map<String, String> information, String outputPath){
        if (!information.containsKey("chl")){
            throw new Exception("chl is not null - data of qrcode")
        }

        String url = "https://chart.googleapis.com/chart?"
        url += information.containsKey("cht")?information.get("cht") + "&" : "cht=qr&"
        url += information.containsKey("chs")?information.get("chs") + "&" : "chs=250x250&"
        url += information.containsKey("choe")?information.get("choe") + "&" : "choe=UTF-8&"
        url += information.containsKey("chld")?information.get("chld") + "&" : "chld=H|1&"
        url += information.get("chl")

        try {
            return ImageIO.read(new URL(url))
        }catch(Exception e){
            log.error e.getMessage()
            return null
        }
    }

    /**
     * Merge logo and QRCode Image.
     * @param image
     * @param logoPath
     * @param outputDir
     * @param outputFileName
     * @return
     */
    def mergeLogo(BufferedImage image, String logoPath, String outputDir, String outputFileName){
        BufferedImage overlay = scaleImage(getLogoImageFrom(logoPath), image.getHeight()/4)
        combineImage(image, overlay, outputDir, outputFileName)
    }

    /**
     *
     * @param image
     * @param logoBase64
     * @param outputDir
     * @param outputFileName
     * @return
     */
    def mergeLogoBase64(BufferedImage image, String logoBase64, String outputDir, String outputFileName){
        BufferedImage overlay = scaleImage(decodeToImage(logoBase64),image.getHeight()/4)
        combineImage(image, overlay, outputDir, outputFileName)
    }

    /**
     *
     * @param image
     * @param overlay
     * @return
     */
    def combineImage(BufferedImage image, BufferedImage overlay, outputDir, outputFileName){
        if(overlay != null){
            BufferedImage combined = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB)
            // paint both images, preserving the alpha channels
            Graphics g = combined.getGraphics()
            g.drawImage(image, 0, 0, null)
            g.drawImage(overlay, (int) (image.getWidth()/2 - overlay.getWidth()/2), (int) (image.getHeight()/2 - overlay.getHeight()/2), null)

            // Save as new image - have logo
            ImageIO.write(combined, "png", new File(outputDir, outputFileName))
        }else{
            // Save image - don't have logo
            ImageIO.write(image, "png", new File(outputDir, outputFileName))
        }
    }

    /**
     *
     * @param imageString
     * @return BufferedImage
     */
    def BufferedImage decodeToImage(String imageString) {

        BufferedImage image
        try {
            byte[] imageByte = imageString.decodeBase64()
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)
            image = ImageIO.read(bis)
            bis.close()
        } catch (Exception e) {
            log.error e.getMessage(), e
        }
        return image
    }

    /**
     *
     * @param imagePath
     * @return BufferedImage
     */
    def BufferedImage getLogoImageFrom(String imagePath){

        BufferedImage img

        UrlValidator urlValidator = new UrlValidator()
        try {
            if (urlValidator.isValid(imagePath)) {
                img = ImageIO.read(new URL(imagePath))
            }else{
                img = ImageIO.read(new File(imagePath))
            }
        } catch (Exception e) {
            log.error e.getMessage()
            return null
        }
        return img
    }

    /**
     * Scale image - using for scaling logo
     * @param imagePath
     * @param maxHeight
     * @return
     */
    def scaleImage(BufferedImage img, float maxHeight){
        if(img == null){
            return null
        }
        def scaleRatio = 1

        if(img.getHeight() > maxHeight){
            scaleRatio = maxHeight/img.getHeight()
        }
        int newWidth = (int) img.width * scaleRatio
        int newHeight = (int) img.height * scaleRatio

        BufferedImage tmpImage = new BufferedImage( newWidth, newHeight, img.type )
        tmpImage.createGraphics().with {
            setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC )
            drawImage( img, 0, 0, newWidth, newHeight, null )
            dispose()
        }
        return tmpImage
    }
}
