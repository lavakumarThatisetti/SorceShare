def extract_text_from_link(a):
    a = a[:-1]
    title_begin = a[:-1].rindex("/")
    title_end = len(a) - 1
    title = a[title_begin + 1:title_end]
    title = " ".join(title.split("-")[:-1])
    return title
