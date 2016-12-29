CREATE TEMPORARY TABLE test1 (
    ref1 int,
    ref2 int,
    ref3 int
);

INSERT INTO test1 VALUES (5,1,1000);
INSERT INTO test1 VALUES (0,1,1000);
INSERT INTO test1 VALUES (0,1,1000);
INSERT INTO test1 VALUES (0,1,1000);
INSERT INTO test1 VALUES (0,1,1000);

SELECT entropy(ref1), entropy(ref2), entropy(ref3) FROM test1;

SELECT norm_entropy(ref1), norm_entropy(ref2), norm_entropy(ref3) FROM test1;

SELECT sumX_log2X(ref1), sumX_log2X(ref2), sumX_log2X(ref3) FROM test1;

SELECT entropy_from_sum(5,sumX_log2X(ref1)), entropy_from_sum(5,sumX_log2X(ref2)), entropy_from_sum(5000,sumX_log2X(ref3)) FROM test1;

SELECT norm_entropy_from_sum(5,sumX_log2X(ref1)), norm_entropy_from_sum(5,sumX_log2X(ref2)), norm_entropy_from_sum(5000,sumX_log2X(ref3)) FROM test1;
