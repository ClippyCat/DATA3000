# A3: Stack-based Postfix Calculator with BST

### Part 1 — Array Stack  (`ADTStack.ArrayStack`)
- Resizable-array implementation of the provided interface.
- Doubles the backing array when full
- Halves it (never below the default capacity of 16) once it drops to a quarter full.
- Throws `EmptyStackException` on `pop`/`peek` when empty.
- `ArrayStack.java`: the implementation
- `ArrayStackTest.java`: self-contained harness with a `main` no JUnit
- run with `java ADTStack.ArrayStackTest`.

### Part 2 — BST core  (`bst`)
`Node` class + `insert`, `search`, `delete` (all three cases: no child,
one child, two children — use in-order successor for two children),
`deleteAll`.

### Part 3 — BST display + BST tests  (`bst`)
`displayTree()` producing the exact `||==> key:value` hierarchical
format from the sample run. Also unit-test Part 2's delete cases.
Pairs with Part 2 inside the `bst` package.

### Part 4 — PostfixCalculator  (`calculator`)
Tokenize on spaces, classify each token (operator / variable / integer),
do the stack evaluation, handle division-by-zero and malformed input.
Depends on the agreed signatures below, not on the finished classes —
can start against stubs immediately.

### Part 5 — Main driver + GUI  (`app`)
Opening JOptionPane explaining the program (press OK to start). Then for
each of the 8 test expressions: set its variables, show the expression,
show the tree (`displayTree()`), show the result, then `deleteAllVariables()`
and show a deletion-confirmation message.

### Part 6 — QA, packaging, presentation lead  (cross-cutting)
Edge-case testing (empty expression, unknown variable, div-by-zero,
malformed token), enforce comments / indentation / naming / JavaDoc
across all packages, build the JAR, write the README, assemble the
presentation (problem, challenges, architecture, code walkthrough).

