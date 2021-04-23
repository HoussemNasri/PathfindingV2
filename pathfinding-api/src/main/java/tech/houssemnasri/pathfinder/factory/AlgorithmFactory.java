package tech.houssemnasri.pathfinder.factory;

import tech.houssemnasri.AlgorithmDescriptor;
import tech.houssemnasri.pathfinder.BaseAlgorithm;

public interface AlgorithmFactory {
     BaseAlgorithm getAlgorithm(AlgorithmDescriptor descriptor);
}
