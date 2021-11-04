# PaletteSwapper
![rock example](https://user-images.githubusercontent.com/6147299/43259782-1362a72a-909d-11e8-8054-eb8216f421c3.png)

A small tool that allows you to change the palettes of multiple image files. It supports both indexed images, but also unindexed images as well.

## Examples
When you run Palette Swapper, it will first ask for a palette definitions text file, and then some images to edit. Multiple palette files and images can be inputted at once.

Palette Swapper has two syntax types for palette definitoons: Brasch syntax and Worms syntax. By default, it will use Brasch syntax. Below are links to examples on making your own palette definitons.

### [Brasch Syntax Mode Example File](https://github.com/SkyAphid/PaletteSwapper/blob/master/PaletteSwapper/RockPaletteVerBrasch.txt)
This syntax is best used for quick one-off palette swaps, and of the two is the most "clear" to read. It only supports one swap per base color. 

### [Worms Syntax Mode Example File](https://github.com/SkyAphid/PaletteSwapper/blob/master/PaletteSwapper/RockPaletteVerWorms.txt)
This syntax is used for larger palette swap jobs and is much more versatile. At the top of the file, insert the base colors. Then indicate a split (or chunk) with a ``-``. Then add the corresponding colors below that you'd want the base colors at the top replaced with.

## Dependencies and Running
Palette Swapper uses Java 1.8

[To run Palette Swapper, simply install Java and then run the JAR.](https://java.com/en/download/)

## Download
[Latest Releases](https://github.com/SkyAphid/PaletteSwapper/releases)
