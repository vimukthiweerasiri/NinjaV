package net.egork.generated.collections.list;

import java.util.NoSuchElementException;
import net.egork.generated.collections.*;
import net.egork.generated.collections.iterator.*;
import net.egork.generated.collections.function.*;
import net.egork.generated.collections.comparator.*;

/**
 * @author Egor Kulikov
 */
public interface CharList extends CharReversableCollection {
    public static final CharList EMPTY = new CharArray(new char[0]);

    public abstract char get(int index);
    public abstract void set(int index, char value);
    public abstract void addAt(int index, char value);
    public abstract void removeAt(int index);

    default public char first() {
        return get(0);
    }

    default public char last() {
    	return get(size() - 1);
    }

    default public void swap(int first, int second) {
    	if (first == second) {
    		return;
    	}
		char temp = get(first);
		set(first, get(second));
		set(second, temp);
    }

    default public CharIterator charIterator() {
        return new CharIterator() {
            private int at;
            private boolean removed;

            public char value() {
                if (removed) {
                    throw new IllegalStateException();
                }
                return get(at);
            }

            public boolean advance() {
                at++;
                removed = false;
                return isValid();
            }

            public boolean isValid() {
                return !removed && at < size();
            }

            public void remove() {
                removeAt(at);
                at--;
                removed = true;
            }
        };
    }

    default public CharIterator reverseIterator() {
        return new CharIterator() {
            private int at = size() - 1;
            private boolean removed;

            public char value() {
                if (removed) {
                    throw new IllegalStateException();
                }
                return get(at);
            }

            public boolean advance() {
                at--;
                removed = false;
                return isValid();
            }

            public boolean isValid() {
                return !removed && at >= 0;
            }

            public void remove() {
                removeAt(at);
                removed = true;
            }
        };
    }

	@Override
    default public void add(char value) {
        addAt(size(), value);
    }

    default public void popLast() {
        removeAt(size() - 1);
    }

    default public void popFirst() {
        removeAt(0);
    }

	default public int minIndex() {
		char result = Character.MAX_VALUE;
	    int size = size();
	    int at = -1;
		for (int i = 0; i < size; i++) {
			char current = get(i);
			if (current < result) {
				result = current;
				at = i;
			}
		}
		return at;
	}

	default public int minIndex(CharComparator comparator) {
		char result = Character.MAX_VALUE;
	    int size = size();
	    int at = -1;
		for (int i = 0; i < size; i++) {
			char current = get(i);
			if (result == Character.MAX_VALUE || comparator.compare(result, current) > 0) {
				result = current;
				at = i;
			}
		}
		return at;
	}

	default public int maxIndex() {
		char result = Character.MIN_VALUE;
	    int size = size();
	    int at = -1;
		for (int i = 0; i < size; i++) {
			char current = get(i);
			if (current > result) {
				result = current;
				at = i;
			}
		}
		return at;
	}

	default public int maxIndex(CharComparator comparator) {
		char result = Character.MAX_VALUE;
	    int size = size();
	    int at = -1;
		for (int i = 0; i < size; i++) {
			char current = get(i);
			if (result == Character.MAX_VALUE || comparator.compare(result, current) < 0) {
				result = current;
				at = i;
			}
		}
		return at;
	}

	default public void sort() {
		sort(CharComparator.DEFAULT);
	}

	default public void sort(CharComparator comparator) {
	    Sorter.sort(this, comparator);
	}

	default public int find(char value) {
	    int size = size();
	    for (int i = 0; i < size; i++) {
	        if (get(i) == value) {
	            return i;
	        }
	    }
	    return -1;
	}

	default public int find(CharFilter filter) {
	    int size = size();
	    for (int i = 0; i < size; i++) {
	        if (filter.accept(get(i))) {
	            return i;
	        }
	    }
	    return -1;
	}

	default public int findLast(char value) {
	    for (int i = size() - 1; i >= 0; i--) {
	        if (get(i) == value) {
	            return i;
	        }
	    }
	    return -1;
	}

	default public int findLast(CharFilter filter) {
	    for (int i = size() - 1; i >= 0; i--) {
	        if (filter.accept(get(i))) {
	            return i;
	        }
	    }
	    return -1;
	}

	default public boolean nextPermutation() {
		return nextPermutation(CharComparator.DEFAULT);
	}

	default public boolean nextPermutation(CharComparator comparator) {
		int size = size();
		char last = get(size - 1);
		for (int i = size - 2; i >= 0; i--) {
			char current = get(i);
			if (comparator.compare(last, current) > 0) {
				for (int j = size - 1; j > i; j--) {
					if (comparator.compare(get(j), current) > 0) {
						swap(i, j);
						subList(i + 1, size).inPlaceReverse();
						return true;
					}
				}
			}
			last = current;
		}
		return false;
	}

	default public void inPlaceReverse() {
	    for (int i = 0, j = size() - 1; i < j; i++, j--) {
	        swap(i, j);
	    }
	}

	default public CharList subList(final int from, final int to) {
	    return new CharList() {
    	    private final int shift;
	        private final int size;

	        {
	            if (from < 0 || from > to || to > CharList.this.size()) {
	                throw new IndexOutOfBoundsException("from = " + from + ", to = " + to + ", size = " + size());
	            }
	            shift = from;
	            size = to - from;
	        }

            public int size() {
                return size;
            }

            public char get(int at) {
                if (at < 0 || at >= size) {
                    throw new IndexOutOfBoundsException("at = " + at + ", size = " + size());
                }
                return CharList.this.get(at + shift);
            }

            public void addAt(int index, char value) {
                throw new UnsupportedOperationException();
            }

            public void removeAt(int index) {
                throw new UnsupportedOperationException();
            }

            public void set(int at, char value) {
                if (at < 0 || at >= size) {
                    throw new IndexOutOfBoundsException("at = " + at + ", size = " + size());
                }
                CharList.this.set(at + shift, value);
            }

            public CharList compute() {
                return new CharArrayList(this);
            }
	    };
	}

	default CharList unique() {
	    char last = Character.MAX_VALUE;
	    CharList result = new CharArrayList();
	    int size = size();
	    for (int i = 0; i < size; i++) {
	        char current = get(i);
	        if (current != last) {
	            result.add(current);
	            last = current;
	        }
	    }
	    return result;
	}
}
