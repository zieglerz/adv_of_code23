import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class HolidayASCIIStringHelperManualArrangementProcedure {

    private ArrayList<LinkedList<Lens>> boxes;
    private int boxCount;

    public HolidayASCIIStringHelperManualArrangementProcedure(int boxCount){
        this.boxCount = boxCount;
        boxes = new ArrayList<>(boxCount);
        
        for(int i = 0; i < boxCount; i++){
            boxes.add(new LinkedList<Lens>());
        }
    }

    public void addLensToBox(Lens lens){
        int boxIndex = getHash(lens.getLabel());
        LinkedList<Lens> box = boxes.get(boxIndex);

        for (int i = 0; i < box.size(); i++) {
            if (box.get(i).getLabel().equals(lens.getLabel())) {
                box.set(i, lens);
                return;
            }
        }
        box.add(lens);
    } 

    public void removeLensFromBox(String label){
        int boxIndex = getHash(label);
        LinkedList<Lens> box = boxes.get(boxIndex);
        Iterator<Lens> iterator = box.iterator();

        while (iterator.hasNext()) {
            Lens currentLens = iterator.next();
            if (currentLens.getLabel().equals(label)) {
                iterator.remove();
                break;
            }
        }
    }
    
    public void printBoxex(boolean printAll){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < boxCount; i++){
            LinkedList<Lens> box = boxes.get(i);
            
            int lensCount = box.size();
            if(!printAll && lensCount == 0){
                continue;
            }

            sb.append("Box " + i + ": ");
            for(int j = 0; j < lensCount; j++){
                sb.append("[");
                sb.append(box.get(j).getLabel());
                sb.append(" ");
                sb.append(box.get(j).getFocalLength());
                sb.append("]");
                if((j+1) < lensCount){
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }

    public int getHash(String string){
        int hash = 0;
        
        for(char c : string.toCharArray()){
            int ascii = (int) c;
            hash += ascii;
            hash = (hash * 17) % boxCount;
        }

        return hash;
    }

    public int calcTotalFocusPower(boolean debugPrints){
        int totalFocusPower = 0;
        for(int i = 0; i < boxCount; i++){
            LinkedList<Lens> box = boxes.get(i);
            for(int j = 0; j < box.size(); j++){
                int lensFocusPower = calcLensFocusPower(i, j, box.get(j).getFocalLength());
                if(debugPrints){
                    System.out.println(box.get(j).getLabel() + " = " + lensFocusPower);
                }
                totalFocusPower += lensFocusPower;
            }
        }
        return totalFocusPower;
    }  
    
    private int calcLensFocusPower(int boxIndex, int lensIndex, int focalLength){
        return (1 + boxIndex) * (lensIndex + 1) * focalLength;
    }
}