# TrashMap
A joke data structure, meant to be a drop-in replacement for any Map in Java.

#### NOTE: Do *not* use this in real code, under any circumstances. It's really just a joke.

#### NOTE 2: Currently unfinished

## General Idea

A `TrashMap` works just like any other `Map` in Java: it maps keys to values. This data structure, however, is special
in that it uses trash analogies for the data storage. It's also intentionally inefficient.

## Implementation details

`TrashMap` is loosely based on the `HashMap` design: items are stored in various "buckets" (here called `TrashCans`),
which contain "entries" (`PieceOfTrash`). The main difference is that I wanted to have my trashcans named after different
types of trash, so there are very few of them. As a result, there are intentionally many items stored in each `TrashCan`, whereas in a
`HashMap`, the storage of multiple entries in one bucket is avoided as much as possible.

The `TrashCan` which a given key-value pair will go into is determined by algorithms in the `Trashifier` class.

For maximum trash analogy realism, the `TrashCan` is implemented as a stack, and it must be "dug" through in a very inefficient
manner to get to the particular piece of trash that the user is looking for. 

## License
MIT