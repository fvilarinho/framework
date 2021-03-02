package br.com.concepting.framework.util;

import br.com.concepting.framework.util.constants.BarcodeConstants;
import br.com.concepting.framework.util.types.BarcodeType;
import br.com.concepting.framework.util.types.ContentType;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Class responsible to manipulate barcodes.
 *
 * @author fvilarinho
 * @since 1.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.</pre>
 */
public class BarcodeGenerator{
    private static BarcodeGenerator instance = null;
    
    /**
     * Returns the instance of the barcode generator.
     *
     * @return Instance that contains the generator.
     */
    public static BarcodeGenerator getInstance(){
        if(instance == null)
            instance = new BarcodeGenerator();
        
        return instance;
    }
    
    /**
     * Generates a barcode image.
     *
     * @param barcodeType Instance that contains the type of the barcode.
     * @param imageType Instance that contains the type of image that will be
     * used.
     * @param value String that contains the value for the barcode.
     * @return Byte array that contains the data of the generated image.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public byte[] generate(BarcodeType barcodeType, ContentType imageType, String value) throws IOException{
        try{
            Barcode barcode = null;
            
            if(value != null && value.length() > 0){
                switch(barcodeType){
                    case BOOKLAND:{
                        barcode = BarcodeFactory.createBookland(value);
                        
                        break;
                    }
                    case CODABAR:{
                        barcode = BarcodeFactory.createCodabar(value);
                        
                        break;
                    }
                    case CODE128:{
                        barcode = BarcodeFactory.createCode128(value);
                        
                        break;
                    }
                    case CODE128A:{
                        barcode = BarcodeFactory.createCode128A(value);
                        
                        break;
                    }
                    case CODE128B:{
                        barcode = BarcodeFactory.createCode128B(value);
                        
                        break;
                    }
                    case CODE128C:{
                        barcode = BarcodeFactory.createCode128C(value);
                        
                        break;
                    }
                    case EAN128:{
                        barcode = BarcodeFactory.createEAN128(value);
                        
                        break;
                    }
                    case EAN13:{
                        barcode = BarcodeFactory.createEAN13(value);
                        
                        break;
                    }
                    case GLOBAL_TRADEITEM_NUMBER:{
                        barcode = BarcodeFactory.createGlobalTradeItemNumber(value);
                        
                        break;
                    }
                    case INTERVEALED_2_OF_5:{
                        barcode = BarcodeFactory.createInt2of5(value, true);
                        
                        break;
                    }
                    case MONARCH:{
                        barcode = BarcodeFactory.createMonarch(value);
                        
                        break;
                    }
                    case PDF417:{
                        barcode = BarcodeFactory.createPDF417(value);
                        
                        break;
                    }
                    case POST_NET:{
                        barcode = BarcodeFactory.createPostNet(value);
                        
                        break;
                    }
                    case RANDOM_WEIGHT_UPCA:{
                        barcode = BarcodeFactory.createRandomWeightUPCA(value);
                        
                        break;
                    }
                    case SCC14_SHIPPING_CODE:{
                        barcode = BarcodeFactory.createSCC14ShippingCode(value);
                        
                        break;
                    }
                    case SHIPMENT_IDENTIFICATION_NUMBER:{
                        barcode = BarcodeFactory.createShipmentIdentificationNumber(value);
                        
                        break;
                    }
                    case SSCC18:{
                        barcode = BarcodeFactory.createSSCC18(value);
                        
                        break;
                    }
                    case STANDARD_2_OF_5:{
                        barcode = BarcodeFactory.createStd2of5(value);
                        
                        break;
                    }
                    case UPCA:{
                        barcode = BarcodeFactory.createUPCA(value);
                        
                        break;
                    }
                    case USD4:{
                        barcode = BarcodeFactory.createUSD4(value);
                        
                        break;
                    }
                    case USPS:{
                        barcode = BarcodeFactory.createUSPS(value);
                        
                        break;
                    }
                    case QRCODE:{
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        QRCodeWriter writer = new QRCodeWriter();
                        BitMatrix matrix = writer.encode(value, BarcodeFormat.QR_CODE, BarcodeConstants.DEFAULT_QRCODE_WIDTH, BarcodeConstants.DEFAULT_QRCODE_HEIGHT);
                        
                        switch(imageType){
                            case JPG:{
                                MatrixToImageWriter.writeToStream(matrix, ContentType.JPG.toString(), outputStream);
                                
                                break;
                            }
                            case GIF:{
                                MatrixToImageWriter.writeToStream(matrix, ContentType.GIF.toString(), outputStream);
                                
                                break;
                            }
                            default:{
                                MatrixToImageWriter.writeToStream(matrix, ContentType.PNG.toString(), outputStream);
                                
                                break;
                            }
                        }
                        
                        return outputStream.toByteArray();
                    }
                    default:
                        break;
                }
            }
            
            if(barcode != null){
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                
                switch(imageType){
                    case JPG:{
                        BarcodeImageHandler.writeJPEG(barcode, outputStream);
                        
                        break;
                    }
                    case GIF:{
                        BarcodeImageHandler.writeGIF(barcode, outputStream);
                        
                        break;
                    }
                    default:{
                        BarcodeImageHandler.writePNG(barcode, outputStream);
                        
                        break;
                    }
                }
                
                return outputStream.toByteArray();
            }
            
            return null;
        }
        catch(OutputException | BarcodeException | WriterException e){
            throw new IOException(e);
        }
    }
}