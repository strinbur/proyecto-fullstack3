export function formatCurrency(value: number, currency = "CLP", locale = "es-CL") {
  try {
    const fmt = new Intl.NumberFormat(locale, {
      style: "currency",
      currency,
      maximumFractionDigits: 0,
    });
    return fmt.format(value);
  } catch (e) {
    // Fallback simple formatting
    return `${value} ${currency}`;
  }
}

export default formatCurrency;
