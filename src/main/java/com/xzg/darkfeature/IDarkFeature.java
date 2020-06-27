package com.xzg.darkfeature;

public interface IDarkFeature {
    boolean enabled();

    boolean dark(long darkTarget);

    boolean dark(String darkTarget);
}
