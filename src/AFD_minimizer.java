package src;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class AFD_minimizer {
    
    public AFD minimize(AFD dfa) {

        Set<State> acceptingStates = new HashSet<>(dfa.getFinalStates());
        Set<State> nonacceptingStates = new HashSet<>(dfa.getStates());
        nonacceptingStates.removeAll(acceptingStates);

        Set<Set<State>> partitions = new HashSet<>();

        partitions.add(acceptingStates);
        partitions.add(nonacceptingStates);

        Queue<Set<State>> worklist = new LinkedList<>();

        worklist.add(acceptingStates);
        worklist.add(nonacceptingStates);

        while(!worklist.isEmpty()) {
            Set<State> partition = worklist.remove();

            for (int ss: dfa.getAlphabet().keySet()) {
                Map<State, Set<State>> transitionMap = new HashMap<>();

                for (State state: partition) {
                    State nexState = dfa.moveState(
                        state,
                        dfa.getAlphabet().get(ss).c_id
                    );
                    Set<State> subpartition = transitionMap.get(nexState);

                    if(subpartition == null) {
                        subpartition = new HashSet<>();
                        transitionMap.put(nexState, subpartition);
                    }
                    subpartition.add(state);
                }
                for (Set<State> subpartition : transitionMap.values()) {
                    if (subpartition.size() < partition.size()) {
                        partitions.remove(partition);
                        partitions.add(subpartition);
                        worklist.add(subpartition);
                    }
                }
            }
        }
        
        Map<State, State> stateMap = new HashMap<>();
        State startState = dfa.getInitialState();
        for (Set<State> partition : partitions) {

            if (partition.isEmpty()) continue;

            State representative = partition.iterator().next();
            for (State state : partition) {
                stateMap.put(state, representative);
            }

            if (partition.contains(startState)) {
                startState = representative;
            }
        }

        AFD minimizedAFD = new AFD(dfa.getAlphabet());
        minimizedAFD.setInitialState(startState);

        for (Set<State> partition : partitions) {

            if (partition.isEmpty()) continue;

            State representative = partition.iterator().next();
            boolean isAccepting = false;
            for (State state : partition) {
                if (acceptingStates.contains(state)) {
                    isAccepting = true;
                    break;
                }
            }

            minimizedAFD.addState(representative, isAccepting);

            for (int ss: dfa.getAlphabet().keySet()) {
                State nexState = dfa.moveState(
                    representative, 
                    dfa.getAlphabet().get(ss).c_id
                );
                State mappedState = stateMap.get(nexState);
                minimizedAFD.addTransition(
                    representative,
                    dfa.getAlphabet().get(ss),
                    mappedState
                );
            }
        }
        
        return minimizedAFD;
    }
}
