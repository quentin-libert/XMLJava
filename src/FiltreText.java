import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;


public class FiltreText implements NodeFilter {
	public short acceptNode(Node n) {
		if(n.getNodeValue() != null && n.getNodeValue().trim().equals("")) return NodeFilter.FILTER_SKIP;
		if(n.getNodeType() == 1 || n.getNodeType() == 3) return NodeFilter.FILTER_ACCEPT;
		else return NodeFilter.FILTER_SKIP;
	}
}
