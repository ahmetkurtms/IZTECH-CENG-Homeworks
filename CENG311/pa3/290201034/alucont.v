module alucont (aluop0, aluop1,f3, f2, f1, f0, gout);

  input aluop0, aluop1, f3, f2, f1, f0;
  output [2:0] gout;
  reg [2:0] gout;

  always @(aluop0 or aluop1 or f3 or f2 or f1 or f0) begin
    if (~(aluop1 | aluop0)) gout = 3'b010;
    else if (aluop1) gout = 3'b110;
    else if (aluop0) begin
      if (~f3 & ~f2 & ~f1 & ~f0) gout = 3'b010;             // ADD
      else if ((~f0) & (~f1) & f2 & (~f3)) gout = 3'b110;   // SUBTRACT
      else if (f2 & f0) gout = 3'b001;                      // OR
      else if (~f0 & f2) gout = 3'b000;                     // AND
      else if (f0 & (~f1) & (~f2) & (~f3)) gout = 3'b100;   // SLL
      else if ((~f0) & f1 & (~f2) & (~f3)) gout = 3'b101;   // SRL
      else if (f0 & f1 & (~f2) & (~f3)) gout = 3'b111;      // NOR
      else gout = 3'b000;
    end
  end
endmodule
