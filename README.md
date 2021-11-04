# PaletteSwapper
## For quickly making recolors of sprites and images
![rock example](https://user-images.githubusercontent.com/6147299/43259782-1362a72a-909d-11e8-8054-eb8216f421c3.png)

PaletteSwapper is a small tool that allows you to recolor images and sprites quickly using basic syntax in an ordinary text file. 
- Can make multiple recolors at once and export multiple files just using one syntax .txt file
- It supports both indexed images, but also unindexed images as well. Outputted images are indexed for you.

![Screenshot 2021-11-04 151440](https://user-images.githubusercontent.com/6147299/140413136-810b3385-3b26-4987-a479-d4abc3da63f8.png)

## Examples
When you run Palette Swapper, it will first ask for a palette definitions text file, and then some images to edit. Multiple palette files and images can be inputted at once. Palette Swapper requires you to make a basic .txt file that acts as the swapping definition, so that it knows what colors to replace and how many files to make.

### [Syntax Example File](https://github.com/SkyAphid/PaletteSwapper/blob/master/PaletteSwapper/RockPalette.txt)
- At the top of the file, insert the base colors.
- Indicate a split (or chunk) with a ``-`` and add corresponding colors that will replace the base colors with. Splits can also indicate that you want to do another recolor and produce another chunk (image), so you can add as many splits as you want.
- Add the corresponding colors below that you'd want the base colors at the top replaced with. Just make sure the orders of your new colors correspond appropriately to the base colors.

## Download
You can download a standalone EXE for the program here. The JAR is included, so you can run it with JRE16 or higher from command line as well if you're on another OS.

[Latest Releases](https://github.com/SkyAphid/PaletteSwapper/releases)
