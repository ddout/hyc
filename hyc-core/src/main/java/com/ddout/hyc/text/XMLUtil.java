package com.ddout.hyc.text;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XMLUtil {
    private static final Logger log = LoggerFactory.getLogger(XMLUtil.class);

    /**
     * @param xml
     * @return Map
     * @description 将xml字符串转换成map
     */
    public static Map<String, Object> readStringXmlOut(String xml) {
        Map<String, Object> map = new HashMap<String, Object>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点

            listNodes(rootElt, map);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * 遍历当前节点元素下面的所有(元素的)子节点
     *
     * @param node
     */
    @SuppressWarnings("unchecked")
    public static void listNodes(Element node, Map<String, Object> map) {
        log.debug("当前节点的名称：：" + node.getName());

        if (!(node.getTextTrim().equals(""))) {
            log.debug("文本内容：：：：" + node.getName() + "=" + node.getText());
            map.put(node.getName(), node.getText());
        }

        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();
        // 遍历
        while (it.hasNext()) {
            // 获取某个子节点对象
            Element e = it.next();
            // 对子节点进行遍历
            listNodes(e, map);
        }
    }

}
