import fs2.{Chunk, Stream}

val s1c = Stream.chunk(Chunk.doubles(Array(1.0, 2.0, 3.0)))
s1c.mapChunks { ds =>
    val doubles = ds.toDoubles
    /* do things unboxed using doubles.{values,size} */
    doubles
  }
