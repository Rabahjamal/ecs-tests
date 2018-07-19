import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {
    
    public MTax(){
        
    }
    
    public static List<String> validate(List<X_Tax> xTaxList) {
        
        List<String> errorList = new ArrayList<>();
        
        if(xTaxList != null && xTaxList.size() > 0) {
            List<String> validIds = new ArrayList<>();
            int cont = 0;
            for (X_Tax tax : xTaxList) {
                final boolean taxId = (tax.getId() != null);
                final boolean nullTax = (tax.getTax() == null);
                final boolean notLocalTax = (!tax.isLocal());

                if(taxId){
                    validIds.add(tax.getId().toString());
                }

                if(nullTax) {
                    errorList.add("El impuesto es obligatorio");
                }
                
                if(notLocalTax){
                    cont++;
                }
            }
            
            final boolean contLessThanOrEqualZero = (cont<=0);
            final boolean validIdsSizeGreaterThanZero = (validIds.size() > 0);

            if(contLessThanOrEqualZero){
                errorList.add("Debe de incluir al menos una tasa no local");
            }
            if(validIdsSizeGreaterThanZero){
                    
                    List<X_Tax> xt = TaxsByListId(validIds, false);
                    final boolean notEqualSizes = (xt.size() != validIds.size());

                    if(notEqualSizes){
                        errorList.add("Existen datos no guardados previamente");
                    }else{
                        HashMap<String, X_Tax> map_taxs = getMapTaxs(xt);
                        updateXTaxList(xTaxList, map_taxs);
                    }
            }
        }
        
        return errorList;
    }

    public static HashMap<String, X_Tax> getMapTaxs(List<X_Tax> xt) {
        HashMap<String, X_Tax> map_taxs = new HashMap<String, X_Tax>();

        for(X_Tax tax: xt){
            map_taxs.put(tax.getId().toString(), tax);
        }
        
        return map_taxs;
    }

    public static void updateXTaxList(List<X_Tax> xTaxList, HashMap<String, X_Tax> map_taxs) {
        for(X_Tax tax: xTaxList) {
          if(tax.getId() != null) {
            tax.setCreated(map_taxs.get(tax.getId().toString()).getCreated());
          }
        }
    }
    
}
