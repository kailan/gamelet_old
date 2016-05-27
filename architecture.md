# Gamelet's Architecture

Gamelet is designed from the ground up to be compatible with many map specification formats (referred to as flavours) created by other plugin developers. The first supported flavour was Overcast Network's [PGM](https://docs.oc.tc). Maps can be automatically loaded and have their flavours detected without manual work.

## Modules
To accomplish the wide map support in a clean way, modules are split into two classes: a main module class that does the work at game-time and a factory for each module that parses the map specification and builds the module as the map is loaded.

When a match is loaded, a ModuleContext is initialised. This provides an interface to the modules to allow for interoperability between them. As modules are loaded, their dependencies are checked and loaded first. Dependencies are soft by default so it is up to the modules to ensure the dependencies they require are loaded. Dependencies for modules can be defined by using the `Module.Dependencies` annotation on the module class.
```
@Module.Dependencies(TeamModule::class, RegionModule::class)
```
Modules can then access other modules through the `ModuleContext`.
```
val teams = ctx[TeamModule::class]!!
```
<br/>

To load a module, the factory's `createModule` function is invoked as shown below:
```
(map: Maplet, ctx: ModuleContext, match: Match) => Module?
```
If a factory is unable to load a module, it can return `null` and the rest of the map and modules will still load successfully.

It is the factory's job to parse data and initialise modules based on the data given. For a module to support PGM-based maps only requires a check for `map.flavour == Maplet.Flavour.PGM` and relevant XML parsing. To get access to the XML element of a map, you can cast the map to `XMLMap` and access the `xml` variable.

If invalid data is provided or there is an error with the module that prevents the map from being functional, the module may throw an exception that will interrupt the map/match loading process.

Registering a new module with the plugin is an easy task:
```
ModuleRegistry.registerModule(TeamModuleFactory()) // pass the factory (not module)
```

## Events
Gamelet relies on events for core tasks such as spawning users and handling deaths. Writing your own module to spawn users at a certain location is easy:
```
@EventHandler
fun spawn(event: PlayerSpawnEvent) {
  val spawn = mySpawns.getSpawnFor(event.player)
  event.spawn = spawn
}
```
It's as easy as that to extend the functionality of matches. By default, core Gamelet modules have their game events listening on `LOW` event priority so overriding with your own listeners won't be a problem.