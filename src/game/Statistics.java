package game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private final Map<String, BornDeath> statisticsMap = new HashMap<>();
    private long tactNumber = 0;

    public void printStatistics(){
        System.out.println(this);
    }

    public void born(String name, Long qty){
        if (qty==0)
            return;
        if(statisticsMap.containsKey(name)) {
            BornDeath bornDeath = statisticsMap.get(name);
            bornDeath.setBorn(bornDeath.getBorn()+qty);
        }else{
            statisticsMap.put(name, new BornDeath(qty, 0L));
        }
    }

    public void death(String name, Long qty){
        if (qty==0)
            return;
        if(statisticsMap.containsKey(name)) {
            BornDeath bornDeath = statisticsMap.get(name);
            bornDeath.setDeath(bornDeath.getDeath()+qty);
        }else{
            statisticsMap.put(name, new BornDeath(0L, qty));
        }

    }
    public void tactIncrement(){
        tactNumber++;
    }

    @Data
    @AllArgsConstructor
    private class BornDeath{
        private Long born;
        private Long death;

        @Override
        public String toString() {
            return born +"/" + death;
        }
    }

    @Override
    public String toString() {
        AtomicInteger counter = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();
        sb.append("Tact: ").append(tactNumber).append("\n");
        statisticsMap.forEach((key, value)->{
            if (counter.getAndIncrement()==4) {
                counter.set(0);
                sb.append("\n");
            }
            sb.append(" ").append(key).append(": ").append(value);
        });
        sb.append("\n");
        return sb.toString();
    }
}
