package com.app;

import com.api.GreetingSupplier;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.ModuleLayer;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {
    private static final String MODULE_NAME = "com.greetings";
    static {
        final Class<Application> clazz = Application.class;
        System.out.printf("Loading %s from classloader %s parent %s.\n",
                clazz, clazz.getClassLoader(), clazz.getClassLoader().getParent());
    }

    public static void main(String[] args) {
        final List<ModuleLayer> layers = createAllLayers(args);
        layers.stream()
                .map(Application::loadServices)
                .forEach(Application::executeServices);
    }

    private static ModuleLayer createLayer(String modulePath) {
        final ModuleFinder finder = ModuleFinder.of(Paths.get(modulePath));
        final ModuleLayer parent = ModuleLayer.boot();
        final Configuration newCfg = parent.configuration().resolve(finder, ModuleFinder.of(), Set.of(MODULE_NAME));
        return parent.defineModulesWithOneLoader(newCfg, ClassLoader.getSystemClassLoader());
    }

    private static List<ModuleLayer> createAllLayers(String[] modulePaths) {
        return Arrays.stream(modulePaths)
                .map(Application::createLayer)
                .collect(Collectors.toList());
    }

    private static ServiceLoader<GreetingSupplier> loadServices(ModuleLayer layer) {
        return ServiceLoader.load(layer, GreetingSupplier.class);
    }

    private static void executeServices(ServiceLoader<GreetingSupplier> serviceLoader) {
        System.out.println(serviceLoader.findFirst().get().getGreeting());
    }
}
