package tech.houssemnasri.impl.toolbox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tech.houssemnasri.api.toolbox.IToolbox;
import tech.houssemnasri.impl.AlgorithmDescriptor;

public class Toolbox implements IToolbox {
  private final Set<AlgorithmDescriptor> includedAlgorithms = new HashSet<>();

  public Toolbox(List<AlgorithmDescriptor> algorithms) {
    includedAlgorithms.addAll(algorithms);
  }

  public Toolbox() {
    this(List.of());
  }

  public Set<AlgorithmDescriptor> getIncludedAlgorithms() {
    return includedAlgorithms;
  }
}
