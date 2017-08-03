package com.ulfric.monty.text;

import com.ulfric.commons.naming.Named;

import java.util.List;
import java.util.function.Function;

public interface Style extends Function<Text, List<String>>, Named {

}