import { describe, it, expect } from "vitest";
import { formatCurrency } from "../utils/format";

describe("formatCurrency", () => {
  it("formatea un valor entero en CLP por defecto", () => {
    const result = formatCurrency(15000);
    expect(result).toContain("15.000");
    expect(result).toContain("$");
  });

  it("redondea sin decimales (maximumFractionDigits: 0)", () => {
    const result = formatCurrency(1999.99);
    expect(result).not.toContain(",99");
    expect(result).not.toContain(".99");
  });

  it("formatea correctamente el valor cero", () => {
    const result = formatCurrency(0);
    expect(result).toContain("0");
  });

  it("acepta una moneda distinta a CLP", () => {
    const result = formatCurrency(100, "USD", "en-US");
    expect(result).toContain("$");
    expect(result).toContain("100");
  });

  it("acepta un locale distinto", () => {
    const result = formatCurrency(2500, "CLP", "es-CL");
    expect(typeof result).toBe("string");
    expect(result.length).toBeGreaterThan(0);
  });

  it("usa el valor por defecto exportado (default export)", () => {
    expect(typeof formatCurrency).toBe("function");
  });
});