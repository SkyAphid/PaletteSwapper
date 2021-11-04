# PaletteSwapper
![rock example](https://user-images.githubusercontent.com/6147299/43259782-1362a72a-909d-11e8-8054-eb8216f421c3.png)

A small tool that allows you to change the palettes of multiple image files. It supports both indexed images, but also unindexed images as well.

## Examples
When you run Palette Swapper, it will first ask for a palette definitions text file, and then some images to edit. Multiple palette files and images can be inputted at once. Palette Swapper requires you to make a basic .txt file that acts as the swapping definition, so that it knows what colors to replace and how many files to make.

### [Syntax Example File](https://github.com/SkyAphid/PaletteSwapper/blob/master/PaletteSwapper/RockPaletteVerWorms.txt)
- At the top of the file, insert the base colors.
- Indicate a split (or chunk) with a ``-`` and add corresponding colors that will replace the base colors with. Splits can also indicate that you want to do another recolor and produce another chunk (image), so you can add as many splits as you want.
- Add the corresponding colors below that you'd want the base colors at the top replaced with. Just make sure the orders of your new colors correspond appropriately to the base colors.


## Dependencies and Running
Palette Swapper uses Java 1.8

[To run Palette Swapper, simply install Java and then run the JAR.](https://java.com/en/download/)

## Download
[Latest Releases](https://github.com/SkyAphid/PaletteSwapper/releases)
