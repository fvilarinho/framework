package br.com.concepting.framework.util.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UtilTypesTest {
    @Test
    public void testComponentTypes() {
        assertEquals("accordion", ComponentType.ACCORDION.getId());
        assertEquals("ACCORDION", ComponentType.ACCORDION.getType());
        assertEquals("button", ComponentType.BUTTON.getId());
        assertEquals("BUTTON", ComponentType.BUTTON.getType());
        assertEquals("clock", ComponentType.CLOCK.getId());
        assertEquals("CLOCK", ComponentType.CLOCK.getType());
        assertEquals("breadCrumb", ComponentType.BREAD_CRUMB.getId());
        assertEquals("BREADCRUMB", ComponentType.BREAD_CRUMB.getType());
        assertEquals("checkBox", ComponentType.CHECK_BOX.getId());
        assertEquals("CHECKBOX", ComponentType.CHECK_BOX.getType());
        assertEquals("colorPicker", ComponentType.COLOR_PICKER.getId());
        assertEquals("COLORPICKER", ComponentType.COLOR_PICKER.getType());
        assertEquals("dialogBox", ComponentType.DIALOG_BOX.getId());
        assertEquals("DIALOGBOX", ComponentType.DIALOG_BOX.getType());
        assertEquals("download", ComponentType.DOWNLOAD.getId());
        assertEquals("DOWNLOAD", ComponentType.DOWNLOAD.getType());
        assertEquals("grid", ComponentType.GRID.getId());
        assertEquals("GRID", ComponentType.GRID.getType());
        assertEquals("gridColumn", ComponentType.GRID_COLUMN.getId());
        assertEquals("GRIDCOLUMN", ComponentType.GRID_COLUMN.getType());
        assertEquals("guide", ComponentType.GUIDE.getId());
        assertEquals("GUIDE", ComponentType.GUIDE.getType());
        assertEquals("guides", ComponentType.GUIDES.getId());
        assertEquals("GUIDES", ComponentType.GUIDES.getType());
        assertEquals("hidden", ComponentType.HIDDEN.getId());
        assertEquals("HIDDEN", ComponentType.HIDDEN.getType());
        assertEquals("image", ComponentType.IMAGE.getId());
        assertEquals("IMAGE", ComponentType.IMAGE.getType());
        assertEquals("label", ComponentType.LABEL.getId());
        assertEquals("LABEL", ComponentType.LABEL.getType());
        assertEquals("link", ComponentType.LINK.getId());
        assertEquals("LINK", ComponentType.LINK.getType());
        assertEquals("list", ComponentType.LIST.getId());
        assertEquals("LIST", ComponentType.LIST.getType());
        assertEquals("loginSessionBox", ComponentType.LOGIN_SESSION_BOX.getId());
        assertEquals("LOGINSESSIONBOX", ComponentType.LOGIN_SESSION_BOX.getType());
        assertEquals("maps", ComponentType.MAPS.getId());
        assertEquals("MAPS", ComponentType.MAPS.getType());
        assertEquals("menuBar", ComponentType.MENU_BAR.getId());
        assertEquals("MENUBAR", ComponentType.MENU_BAR.getType());
        assertEquals("menuItem", ComponentType.MENU_ITEM.getId());
        assertEquals("MENUITEM", ComponentType.MENU_ITEM.getType());
        assertEquals("menuItemSeparator", ComponentType.MENU_ITEM_SEPARATOR.getId());
        assertEquals("MENUITEMSEPARATOR", ComponentType.MENU_ITEM_SEPARATOR.getType());
        assertEquals("option", ComponentType.OPTION.getId());
        assertEquals("OPTION", ComponentType.OPTION.getType());
        assertEquals("options", ComponentType.OPTIONS.getId());
        assertEquals("OPTIONS", ComponentType.OPTIONS.getType());
        assertEquals("pager", ComponentType.PAGER.getId());
        assertEquals("PAGER", ComponentType.PAGER.getType());
        assertEquals("password", ComponentType.PASSWORD.getId());
        assertEquals("PASSWORD", ComponentType.PASSWORD.getType());
        assertEquals("progressBar", ComponentType.PROGRESS_BAR.getId());
        assertEquals("PROGRESSBAR", ComponentType.PROGRESS_BAR.getType());
        assertEquals("radio", ComponentType.RADIO.getId());
        assertEquals("RADIO", ComponentType.RADIO.getType());
        assertEquals("searchPropertiesGroup", ComponentType.SEARCH_PROPERTIES_GROUP.getId());
        assertEquals("SEARCHPROPERTIESGROUP", ComponentType.SEARCH_PROPERTIES_GROUP.getType());
        assertEquals("section", ComponentType.SECTION.getId());
        assertEquals("SECTION", ComponentType.SECTION.getType());
        assertEquals("textBox", ComponentType.TEXT_BOX.getId());
        assertEquals("TEXT", ComponentType.TEXT_BOX.getType());
        assertEquals("textBox", ComponentType.SLIDER_BAR.getId());
        assertEquals("TEXT", ComponentType.SLIDER_BAR.getType());
        assertEquals("textBox", ComponentType.SPINNER.getId());
        assertEquals("TEXT", ComponentType.SPINNER.getType());
        assertEquals("textBox", ComponentType.SUGGESTION_BOX.getId());
        assertEquals("TEXT", ComponentType.SUGGESTION_BOX.getType());
        assertEquals("timer", ComponentType.TIMER.getId());
        assertEquals("TIMER", ComponentType.TIMER.getType());
        assertEquals("textArea", ComponentType.TEXT_AREA.getId());
        assertEquals("TEXTAREA", ComponentType.TEXT_AREA.getType());
        assertEquals("textBox", ComponentType.CALENDAR.getId());
        assertEquals("TEXT", ComponentType.CALENDAR.getType());
        assertEquals("treeView", ComponentType.TREE_VIEW.getId());
        assertEquals("TREEVIEW", ComponentType.TREE_VIEW.getType());
        assertEquals("upload", ComponentType.UPLOAD.getId());
        assertEquals("FILE", ComponentType.UPLOAD.getType());
    }

    @Test
    public void testByteMetricType() {
        assertEquals("byte", ByteMetricType.BYTE.getUnit());
        assertEquals(1d, ByteMetricType.BYTE.getValue(), 0);
        assertEquals("KB", ByteMetricType.KILO_BYTE.getUnit());
        assertEquals(1024d, ByteMetricType.KILO_BYTE.getValue(), 0);
        assertEquals("MB", ByteMetricType.MEGA_BYTE.getUnit());
        assertEquals(Math.pow(1024d, 2), ByteMetricType.MEGA_BYTE.getValue(), 0);
        assertEquals("GB", ByteMetricType.GIGA_BYTE.getUnit());
        assertEquals(Math.pow(1024d, 3), ByteMetricType.GIGA_BYTE.getValue(), 0);
        assertEquals("TB", ByteMetricType.TERA_BYTE.getUnit());
        assertEquals(Math.pow(1024d, 4), ByteMetricType.TERA_BYTE.getValue(), 0);
        assertEquals("PB", ByteMetricType.PETA_BYTE.getUnit());
        assertEquals(Math.pow(1024d, 5), ByteMetricType.PETA_BYTE.getValue(), 0);
    }

    @Test
    public void testBitMetricType() {
        assertEquals("bit", BitMetricType.BIT.getUnit());
        assertEquals(1d, BitMetricType.BIT.getValue(), 0);
        assertEquals("KBit", BitMetricType.KILO_BIT.getUnit());
        assertEquals(1000d, BitMetricType.KILO_BIT.getValue(), 0);
        assertEquals("MBit", BitMetricType.MEGA_BIT.getUnit());
        assertEquals(Math.pow(1000d, 2), BitMetricType.MEGA_BIT.getValue(), 0);
        assertEquals("GBit", BitMetricType.GIGA_BIT.getUnit());
        assertEquals(Math.pow(1000d, 3), BitMetricType.GIGA_BIT.getValue(), 0);
    }

    @Test
    public void testFormulaType() {
        assertEquals("none", FormulaType.NONE.getId());
        assertEquals("count", FormulaType.COUNT.getId());
        assertEquals("sum", FormulaType.SUM.getId());
        assertEquals("min", FormulaType.MIN.getId());
        assertEquals("max", FormulaType.MAX.getId());
        assertEquals("avg", FormulaType.AVERAGE.getId());
    }

    @Test
    public void testContentType() {
        assertNull(ContentType.NONE.getMimeType());
        assertNull(ContentType.NONE.getExtension());
        assertEquals(ContentType.NONE, ContentType.toContentType(null));
        assertEquals(ContentType.HTML, ContentType.toContentType("text/html"));
        assertEquals(ContentType.HTML, ContentType.toContentType(".html"));
        assertEquals(ContentType.HTML, ContentType.toContentType("html"));
        assertEquals(ContentType.HTML, ContentType.toContentType("text/html;charset=UTF-8"));
        assertEquals("text/html", ContentType.HTML.getMimeType());
        assertEquals(".html", ContentType.HTML.getExtension());
        assertEquals("text/css", ContentType.CSS.getMimeType());
        assertEquals(".css", ContentType.CSS.getExtension());
        assertEquals("text/javascript", ContentType.JS.getMimeType());
        assertEquals(".js", ContentType.JS.getExtension());
        assertEquals("image/png", ContentType.PNG.getMimeType());
        assertEquals(".png", ContentType.PNG.getExtension());
        assertEquals("image/jpeg", ContentType.JPG.getMimeType());
        assertEquals(".jpg", ContentType.JPG.getExtension());
        assertEquals("image/gif", ContentType.GIF.getMimeType());
        assertEquals(".gif", ContentType.GIF.getExtension());
        assertEquals("text/plain", ContentType.TXT.getMimeType());
        assertEquals(".txt", ContentType.TXT.getExtension());
        assertEquals("text/csv", ContentType.CSV.getMimeType());
        assertEquals(".csv", ContentType.CSV.getExtension());
        assertEquals("text/rtf", ContentType.RTF.getMimeType());
        assertEquals(".rtf", ContentType.RTF.getExtension());
        assertEquals("text/xml", ContentType.XML.getMimeType());
        assertEquals(".xml", ContentType.XML.getExtension());
        assertEquals("application/json", ContentType.JSON.getMimeType());
        assertEquals(".json", ContentType.JSON.getExtension());
        assertEquals("application/pdf", ContentType.PDF.getMimeType());
        assertEquals(".pdf", ContentType.PDF.getExtension());
        assertEquals("application/msword", ContentType.DOC.getMimeType());
        assertEquals(".doc", ContentType.DOC.getExtension());
        assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ContentType.DOCX.getMimeType());
        assertEquals(".docx", ContentType.DOCX.getExtension());
        assertEquals("application/vnd.ms-excel", ContentType.XLS.getMimeType());
        assertEquals(".xls", ContentType.XLS.getExtension());
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ContentType.XLSX.getMimeType());
        assertEquals(".xlsx", ContentType.XLSX.getExtension());
        assertEquals("application/vnd.ms-powerpoint", ContentType.PPT.getMimeType());
        assertEquals(".ppt", ContentType.PPT.getExtension());
        assertEquals("application/vnd.openxmlformats-officedocument.presentationml.presentation", ContentType.PPTX.getMimeType());
        assertEquals(".pptx", ContentType.PPTX.getExtension());
        assertEquals("application/octet-stream", ContentType.BINARY.getMimeType());
        assertNull(ContentType.BINARY.getExtension());
        assertEquals("application/x-www-form-urlencoded", ContentType.ACTION_FORM_DATA.getMimeType());
        assertNull(ContentType.ACTION_FORM_DATA.getExtension());
        assertEquals("multipart/form-data", ContentType.MULTIPART_DATA.getMimeType());
        assertNull(ContentType.MULTIPART_DATA.getExtension());
        assertEquals("multipart/alternative", ContentType.MULTIPART_ALTERNATIVE.getMimeType());
        assertNull(ContentType.MULTIPART_ALTERNATIVE.getExtension());
        assertEquals("multipart/related", ContentType.MULTIPART_RELATED.getMimeType());
        assertNull(ContentType.MULTIPART_RELATED.getExtension());
        assertEquals("multipart/mixed", ContentType.MULTIPART_MIXED.getMimeType());
        assertNull(ContentType.MULTIPART_MIXED.getExtension());
    }

    @Test
    public void testSortOrderType() {
        assertEquals("", SortOrderType.NONE.getId());
        assertEquals("asc", SortOrderType.ASCEND.getId());
        assertEquals("desc", SortOrderType.DESCEND.getId());
    }
}
