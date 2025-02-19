module alu32(
  output reg [31:0] alu_out,
  input [31:0] a, b,
  output reg zout,
  input [5:0] shamt,
  input [2:0] alu_control
);

  // ALU Operations
  always @(a or b or shamt or alu_control) begin
    case (alu_control)
      3'b000: alu_out = a & b;          // AND
      3'b001: alu_out = a | b;          // OR
      3'b010: alu_out = a + b;          // ADD
      3'b110: alu_out = a - b;          // SUBTRACT
      3'b111: alu_out = ~(a | b);       // NOR
      3'b100: alu_out = b << shamt;     // SLL
      3'b101: alu_out = b >> shamt;     // SRL
      default: alu_out = 32'bx;
    endcase
    zout = ~(|alu_out);
  end

endmodule
